package ac.simons.springio2016.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ThymeleafBannerAutoConfiguration {   
    @Bean
    public SomeBean someBean() {
	return new SomeBean();		
    }
}
