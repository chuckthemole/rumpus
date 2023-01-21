package com.rumpus.rumpus;

// import kong.unirest.JsonNode;
// import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


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
