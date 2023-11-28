package com.rumpus;

// import kong.unirest.JsonNode;
// import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rumpus.chuck.config.ChuckConfig;
import com.rumpus.rumpus.config.RumpusConfig;


@SpringBootApplication
// @Configuration
// @EnableAutoConfiguration
// @Import({ RumpusConfig.class, ChuckConfig.class })
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
