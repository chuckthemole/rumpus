package com.rumpus.rumpus.config;

import org.springframework.boot.test.context.TestConfiguration;

import com.rumpus.common.Config.AbstractJdbcHttpSessionConfig;

@TestConfiguration
public class JdbcHttpSessionTestConfig extends AbstractJdbcHttpSessionConfig {
    public JdbcHttpSessionTestConfig() {
        
    }
}
