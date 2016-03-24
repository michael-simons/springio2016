package ac.simons.springio2016.starter;

import java.util.ArrayList;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureBefore(ThymeleafAutoConfiguration.class)
class ThymeleafBannerAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "spring.main.banner-mode", havingValue = "off")
    @Order(-20)
    public BannerSupplier emptyBannerSupplier() {
	return args -> new ArrayList<>();
    }

    @Bean
    @ConditionalOnMissingBean(BannerSupplier.class)
    @Order(-10)
    public BannerSupplier defaultBannerSupplier (
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
