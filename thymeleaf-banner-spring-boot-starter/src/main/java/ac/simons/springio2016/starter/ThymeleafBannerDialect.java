package ac.simons.springio2016.starter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.thymeleaf.Arguments;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
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