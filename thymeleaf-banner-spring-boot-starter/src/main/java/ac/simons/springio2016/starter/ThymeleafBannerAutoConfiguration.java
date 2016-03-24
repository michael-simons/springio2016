package ac.simons.springio2016.starter;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureBefore(ThymeleafAutoConfiguration.class)
@AutoConfigureAfter(CacheAutoConfiguration.class)
class ThymeleafBannerAutoConfiguration {

    @Bean    
    @ConditionalOnBean(CacheManager.class)
    @ConditionalOnProperty("thymeleaf-banner.cacheName")
    @Order(-30)
    public BannerSupplier bannerlessBannerSupplier(CacheManager cacheManager, @Value("${thymeleaf-banner.cacheName}") String cacheName) {
	return new BannerlessBannerSupplier(cacheManager.getCache(cacheName));
    }

    @Bean
    @ConditionalOnMissingBean(BannerSupplier.class)
    @ConditionalOnProperty(name = "spring.main.banner-mode", havingValue = "off")
    @Order(-20)
    public BannerSupplier emptyBannerSupplier() {
	return args -> new ArrayList<>();
    }

    @Bean
    @ConditionalOnMissingBean(BannerSupplier.class)
    @Order(-10)
    public BannerSupplier defaultBannerSupplier(
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
