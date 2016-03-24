package ac.simons.springio2016.starter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.thymeleaf.Arguments;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

class ThymeleafBannerDialect extends AbstractDialect {

    private final BannerSupplier bannerSupplier;

    public ThymeleafBannerDialect(BannerSupplier bannerSupplier) {
	this.bannerSupplier = bannerSupplier;	
    }

    @Override
    public String getPrefix() {
	return "banner";
    }

    @Override
    public Set<IProcessor> getProcessors() {
	final Set<IProcessor> processors = new HashSet<>();
	
	processors.add(new AbstractMarkupSubstitutionElementProcessor("enableansibanners") {
	    @Override
	    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
		final Element script = new Element("script");
		script.setAttribute("type", "text/javascript");
		script.setAttribute("src", "/js/ansi_up.js");
		
		final Element ansi2html = new Element("script");
		script.setAttribute("type", "text/javascript");				
		ansi2html.addChild(new Text("\n"
			+ "document.addEventListener('DOMContentLoaded', function(event) {\n"
			+ "   var banner = document.getElementsByClassName('banner');\n"
			+ "   for (var i = 0; i < banner.length; ++i) {\n"
			+ "      banner[i].innerHTML = ansi_up.ansi_to_html(banner[i].innerHTML);\n"
			+ "   }\n"
			+ "});\n"
		,null,null,true ));		
		
		return Arrays.asList(script, ansi2html);
	    }

	    @Override
	    public int getPrecedence() {
		return 1000;
	    }	    
	});
		
	processors.add(new AbstractMarkupSubstitutionElementProcessor("show") {
	    @Override
	    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
		return bannerSupplier.get(arguments);
	    }

	    @Override
	    public int getPrecedence() {
		return 1000;
	    }
	});
	return processors;
    }    
}