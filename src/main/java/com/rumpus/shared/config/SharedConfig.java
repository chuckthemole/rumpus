package com.rumpus.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Security.Authentication.AuthenticationChecker;

@Configuration
public class SharedConfig {

    @Bean
    public AuthenticationChecker authChecker() {
        return new AuthenticationChecker();
    }
    
}
