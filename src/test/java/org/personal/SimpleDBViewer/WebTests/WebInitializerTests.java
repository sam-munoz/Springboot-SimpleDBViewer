package org.personal.SimpleDBViewer.WebTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
public class WebInitializerTests {
    @Autowired
    private ConfigurableApplicationContext webContext;
    @Test
    void printAllBeans() {
        for(String beanName : webContext.getBeanDefinitionNames())  {
            System.out.println(beanName);
        }
    }
}
