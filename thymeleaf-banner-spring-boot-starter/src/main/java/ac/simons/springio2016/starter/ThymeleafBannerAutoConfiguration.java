package ac.simons.springio2016.starter;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureBefore(ThymeleafAutoConfiguration.class)
class ThymeleafBannerAutoConfiguration {
    
    @Bean
    public BannerSupplier bannerSupplier(
	    final ResourceLoader resourceLoader,
	    final Environment environment
    ) throws ClassNotFoundException {
	return new ThymeleafBannerSupplier(environment, resourceLoader);
    }
        
    @Bean
    public ThymeleafBannerDialect webBannerDialect(final BannerSupplier bannerSupplier) {
	return new ThymeleafBannerDialect(bannerSupplier);
    }
}
