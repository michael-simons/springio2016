package ac.simons.springio2016.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
class ThymeleafBannerAutoConfiguration {
}
