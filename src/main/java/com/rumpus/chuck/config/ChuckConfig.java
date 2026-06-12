package com.rumpus.chuck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserFactory;
import com.rumpus.rumpus.views.RumpusAdminUserView;

/**
 * TODO: think about overriding dataSource() to use a different database for
 * different packages.
 */
@Configuration
@ComponentScan("com.rumpus.chuck")
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "rumpus")
// @org.springframework.context.annotation.PropertySource(value =
// "classpath:properties.yml", factory =
// com.rumpus.common.Config.Properties.yaml.YamlPropertySourceFactory.class)
public class ChuckConfig extends AbstractCommonConfig {

    @Autowired
    public ChuckConfig(Environment environment) {
        super(environment);
    }

    @Bean
    public RumpusAdminUserView chuckAdminUserView() {
        RumpusUserFactory userFactory = new RumpusUserFactory();
        RumpusUser user = userFactory.createEmpty();
        user.setUsername("EMPTY_USERNAME");
        user.setEncodedPassword("EMPTY_PASSWORD");
        user.setEmail("EMPTY_EMAIL");
        return new RumpusAdminUserView(user, userFactory);
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
