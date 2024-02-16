package com.rumpus.rumpus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@Configuration
@EnableJdbcHttpSession
// @PropertySource("classpath:application.properties")
@org.springframework.context.annotation.PropertySource(value = "classpath:properties.yml", factory = com.rumpus.common.Config.Properties.YamlPropertySourceFactory.class)
public class JdbcHttpSessionTestConfig extends JdbcHttpSessionConfig {
}
