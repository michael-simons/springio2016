package ac.simons.springio2016.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@EnableCaching
public class Application {

    @Controller
    static class DemoController {

	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index() {
	    return "index";
	}
    }

    public static void main(String... args) {
	SpringApplication.run(Application.class, args);
    }
}
