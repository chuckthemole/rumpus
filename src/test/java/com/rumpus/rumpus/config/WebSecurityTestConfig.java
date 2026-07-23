package com.rumpus.rumpus.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.rumpus.shared.config.WebSecurityConfig;

import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@EnableWebSecurity
public class WebSecurityTestConfig extends WebSecurityConfig {

    public WebSecurityTestConfig() {
    }
}
