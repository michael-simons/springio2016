package ac.simons.springio2016.starter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.ResourceBanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;

class ThymeleafBannerSupplier implements BannerSupplier {

    private final String banner;

    public ThymeleafBannerSupplier(final Environment environment, final ResourceLoader resourceLoader) {
	final String bannerLocation = environment.getProperty("banner.location", "classpath:banner.txt");
	final String bannerCharset = environment.getProperty("banner.charset", "UTF-8");
	final Resource resource = resourceLoader.getResource(bannerLocation);

	try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
	    final Banner banner;
	    if (resource.exists()) {
		banner = new ResourceBanner(resource);
	    } else {
		banner = BeanUtils.instantiateClass((Class<Banner>) Class.forName("org.springframework.boot.SpringBootBanner"));
	    }

	    banner.printBanner(environment, this.getClass(), new PrintStream(out));
	    this.banner = new String(out.toByteArray(), Charset.forName(bannerCharset));
	} catch (IOException | ClassNotFoundException e) {
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
