package com.rumpus.rumpus.config;

import org.springframework.boot.test.context.TestConfiguration;

import com.rumpus.common.Config.Session.CommonJdbcHttpSessionConfig;

@TestConfiguration
public class JdbcHttpSessionTestConfig extends CommonJdbcHttpSessionConfig {
    public JdbcHttpSessionTestConfig() {

    }
}
