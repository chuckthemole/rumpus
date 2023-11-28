package com.rumpus.rumpus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusTestConfig extends RumpusConfig {

    // TODO:
    // Added this 2023/11/28 to fix the following error:
    // Please ensure Spring Security & Spring MVC are configured in a shared ApplicationContext.
    // Maybe look into a bit more
    @Bean(name = "mvcHandlerMappingIntrospector")
	public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}
}
