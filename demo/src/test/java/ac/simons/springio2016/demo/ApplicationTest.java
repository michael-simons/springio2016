package ac.simons.springio2016.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ApplicationTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void autoconfigurationShouldBeUsed() {
	Assert.assertTrue(applicationContext.containsBean("someBean"));
    }
}