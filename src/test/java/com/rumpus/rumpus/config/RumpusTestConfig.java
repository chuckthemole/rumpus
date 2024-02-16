package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@ComponentScan("com.rumpus.rumpus")
// @PropertySource("classpath:database.properties")
@org.springframework.context.annotation.PropertySource(value = "classpath:properties.yml", factory = com.rumpus.common.Config.Properties.YamlPropertySourceFactory.class)
public class RumpusTestConfig extends RumpusConfig {

    @Autowired
    public RumpusTestConfig(Environment environment) {
        super(environment);
    }

    // TODO:
    // Added this 2023/11/28 to fix the following error:
    // Please ensure Spring Security & Spring MVC are configured in a shared ApplicationContext.
    // Maybe look into a bit more
    @Bean(name = "mvcHandlerMappingIntrospector")
	public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}
}
