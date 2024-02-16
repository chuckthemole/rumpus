package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityTestConfig extends WebSecurityConfig {

    @Autowired
    public WebSecurityTestConfig(Environment environment) {
        super(environment);
    }
}
