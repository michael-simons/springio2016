package ac.simons.springio2016.starter;

import java.util.List;
import java.util.Optional;
import org.springframework.cache.Cache;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Node;

class BannerlessBannerSupplier implements BannerSupplier {
    private final Cache cache;
   
    public BannerlessBannerSupplier(final Cache cache) {
	this.cache = cache;
    }

    @Override
    public List<Node> get(Arguments args) {	
	final List<Node> rv = Optional.ofNullable(cache.get("bannerlessBanner", List.class))
		.orElseGet(() ->  args.getTemplateRepository().getFragment(args, "<blockquote class=\"twitter-tweet\" data-conversation=\"none\" data-lang=\"de\"><p lang=\"en\" dir=\"ltr\">there are some SICK, <a href=\"https://twitter.com/springboot\">@SpringBoot</a>-bannerless people on the internet. <br /><br />stay banner’d, my friends.</p>&mdash; Josh Long (龙之春) (@starbuxman) <a href=\"https://twitter.com/starbuxman/status/702215432946593792\">23. Februar 2016</a></blockquote><script async=\"async\" src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>"));
	cache.putIfAbsent("bannerlessBanner", rv);
	return rv;
    }
}
