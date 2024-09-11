package com.rumpus.chuck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.views.RumpusAdminUserView;

/**
 * TODO: think about overriding dataSource() to use a different database for different packages.
 */
@Configuration
@ComponentScan("com.rumpus.chuck")
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "rumpus")
@org.springframework.context.annotation.PropertySource(value = "classpath:properties.yml", factory = com.rumpus.common.Config.Properties.YamlPropertySourceFactory.class)
public class ChuckConfig extends AbstractCommonConfig {

    public static final String NAME = "ChuckConfig";

    @Autowired
    public ChuckConfig(Environment environment) {
        super(NAME, environment);
    }

    @Bean
    public RumpusAdminUserView chuckAdminUserView() {
        return RumpusAdminUserView.createWithUser(RumpusUser.create("EMPTY_USERNAME", "EMPTY_PASSWORD", "EMPTY_EMAIL"));
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }
}
