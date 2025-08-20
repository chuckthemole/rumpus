package com.rumpus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App implements WebMvcConfigurer {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(App.class);

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        // Default to "rumpus" app
        String targetApp = "rumpus";

        // Check args for flag like: --app=chuck
        for (String arg : args) {
            if (arg.startsWith("--app=")) {
                targetApp = arg.substring("--app=".length());
            }
        }

        Class<?> appClass;
        switch (targetApp.toLowerCase()) {
            case "chuck":
                System.out.println("Running ChuckApp");
                appClass = com.chuck.ChuckApp.class;
                break;
            case "rumpus":
                System.out.println("Running RumpusApp");
                appClass = com.rumpus.RumpusApp.class;
                break;
            case "buildshift":
                System.out.println("Running BuildShiftApp");
                appClass = com.buildshift.BuildShiftApp.class;
                break;
            default:
                throw new IllegalArgumentException("Unknown app: " + targetApp);
        }

        LOGGER.info("Starting application for target: {}", targetApp);
        SpringApplication app = new SpringApplication(appClass);
        applicationContext = app.run(args);

        // Optional: dump beans
        // displayAllBeans();
    }

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("* * * Beans List * * * ");
        for (String beanName : allBeanNames) {
            System.out.println(beanName);
        }
        System.out.println("* * * Beans List End * * * ");
    }

    @org.springframework.context.event.EventListener
    public void handleContextRefresh(org.springframework.context.event.ContextRefreshedEvent event) {
        LOGGER.info("App::handleContextRefresh()");
        final org.springframework.core.env.Environment environment = event.getApplicationContext().getEnvironment();
        LOGGER.info("Active profiles: {}", java.util.Arrays.toString(environment.getActiveProfiles()));
    }

    @Override
    public void addResourceHandlers(
            org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/WEB-INF/images/")
                .setCacheControl(org.springframework.http.CacheControl.maxAge(2, java.util.concurrent.TimeUnit.HOURS)
                        .cachePublic());
    }
}
