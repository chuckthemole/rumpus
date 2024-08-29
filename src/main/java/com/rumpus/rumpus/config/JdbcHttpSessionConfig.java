package com.rumpus.rumpus.config;

import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Config.AbstractJdbcHttpSessionConfig;

@Configuration
public class JdbcHttpSessionConfig extends AbstractJdbcHttpSessionConfig {
    public JdbcHttpSessionConfig() {
        super();
    }
 }