package com.rumpus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

// TODO: @SpringBootApplication is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan with their default attributes.
// think about customizing the attributes of these annotations. Something to look into
// @Configuration
// @EnableAutoConfiguration
// @Import({ RumpusConfig.class, ChuckConfig.class })

@SpringBootApplication
public class App {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(App.class, args);
        // displayAllBeans();
    }

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("* * * Beans List * * * ");
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
        System.out.println("* * * Beans List End * * * ");
    }
}
