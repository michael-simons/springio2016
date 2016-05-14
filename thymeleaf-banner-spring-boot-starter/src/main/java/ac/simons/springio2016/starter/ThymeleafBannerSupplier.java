package ac.simons.springio2016.starter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;

class ThymeleafBannerSupplier implements BannerSupplier {

    private final String banner;

    public ThymeleafBannerSupplier(final Banner banner, final Environment environment) {
	try (
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final PrintStream printStream = new PrintStream(out)) {
	    banner.printBanner(environment, this.getClass(), printStream);
	    this.banner = new String(out.toByteArray(), StandardCharsets.UTF_8);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public List<Node> get(final Arguments args) {
	final Element node = new Element("pre");
	node.setAttribute("class", "banner");
	node.addChild(new Text(banner));
	return Arrays.asList(node);
    }

}
