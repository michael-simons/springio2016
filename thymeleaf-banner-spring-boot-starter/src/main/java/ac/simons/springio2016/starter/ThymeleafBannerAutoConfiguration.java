package ac.simons.springio2016.starter;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureBefore(ThymeleafAutoConfiguration.class)
@AutoConfigureAfter(CacheAutoConfiguration.class)
class ThymeleafBannerAutoConfiguration {

    static class OnNoBannerButFun extends AllNestedConditions {
	
	public OnNoBannerButFun() {
	    // This controlls wether beans are added or not.
	    // If you would want to prevent configuration classes,
	    // use ConfigurationPhase.PARSE_CONFIGURATION
	    super(ConfigurationPhase.REGISTER_BEAN);
	}
	
	// See https://github.com/spring-projects/spring-boot/issues/2541
	@ConditionalOnProperty(name = "spring.main.banner-mode", havingValue = "off")	
	static class OnBannerTurnedOff{}
	
	@ConditionalOnBean(CacheManager.class)
	static class OnCacheManagerAvailable{}
	
	@ConditionalOnProperty("thymeleaf-banner.cacheName")
	static class OnCacheNameSpecified{}	
    }
    
    @Bean    
    @Conditional(OnNoBannerButFun.class)    
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
	    final Banner banner,
	    final Environment environment
    ) throws ClassNotFoundException {
	return new ThymeleafBannerSupplier(banner, environment);
    }

    @Bean
    public ThymeleafBannerDialect webBannerDialect(final BannerSupplier bannerSupplier) {
	return new ThymeleafBannerDialect(bannerSupplier);
    }
}
