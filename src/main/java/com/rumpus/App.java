package com.rumpus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// TODO: @SpringBootApplication is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan with their default attributes.
// think about customizing the attributes of these annotations. Something to look into
// @Configuration
// @EnableAutoConfiguration
// @Import({ RumpusConfig.class, ChuckConfig.class })

@SpringBootApplication // TODO: can I put this annotation on abstract class in common and extend here and other places?
public class App implements WebMvcConfigurer {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(App.class);
    
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        SpringApplication applicationContext = new SpringApplication(App.class);
        // applicationContext.setDefaultProperties(java.util.Collections.singletonMap("server.port", "8083"));
        // applicationContext = SpringApplication.run(App.class, args);
        applicationContext.run(args);
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

    @org.springframework.context.event.EventListener
    public void handleContextRefresh(org.springframework.context.event.ContextRefreshedEvent event) {
        LOGGER.info("App::handleContextRefresh()");
        final org.springframework.core.env.Environment environment = event.getApplicationContext().getEnvironment();
        LOGGER.info("Active profiles: {}", java.util.Arrays.toString(environment.getActiveProfiles()));

        final org.springframework.core.env.MutablePropertySources sources = ((org.springframework.core.env.AbstractEnvironment) environment).getPropertySources();

        java.util.stream.StreamSupport.stream(sources.spliterator(), false)
            .filter(propertySource -> propertySource instanceof org.springframework.core.env.EnumerablePropertySource)
            .map(propertySource -> ((org.springframework.core.env.EnumerablePropertySource) propertySource).getPropertyNames())
            .flatMap(java.util.Arrays::stream)
            .distinct()
            .filter(prop -> !(prop.contains("credentials") || prop.contains("password")))
            .forEach(prop -> LOGGER.info("{}: {}", prop, environment.getProperty(prop)));
    }

    // Added to serve static images from /WEB-INF/images
    // you can access them from /images
    @Override
    public void addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/images/**")
            .addResourceLocations("/WEB-INF/images/") // should I add static in resources here, too?
            .setCacheControl(org.springframework.http.CacheControl.maxAge(2, java.util.concurrent.TimeUnit.HOURS).cachePublic());
    }
}
