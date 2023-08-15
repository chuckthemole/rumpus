package com.rumpus.rumpus.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusTestConfig extends RumpusConfig {
}
