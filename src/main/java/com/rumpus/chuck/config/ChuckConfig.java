package com.rumpus.chuck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.views.RumpusAdminUserView;

/**
 * TODO: think about overriding dataSource() to use a different database for different packages.
 */
@Configuration
@ComponentScan("com.rumpus.chuck")
public class ChuckConfig extends AbstractCommonConfig {

    @Bean
    public RumpusAdminUserView chuckAdminUserView() {
        return RumpusAdminUserView.create(RumpusUser.create("EMPTY_USERNAME", "EMPTY_PASSWORD", "EMPTY_EMAIL"));
    }
}
