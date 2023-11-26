package com.rumpus.chuck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.rumpus.chuck.views.ChuckViewLoader;
import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.views.AbstractViewLoader;
import com.rumpus.rumpus.views.RumpusViewLoader;

/**
 * TODO: think about overriding dataSource() to use a different database for different packages.
 */
@Configuration
@ComponentScan("com.rumpus.chuck")
public class ChuckConfig extends AbstractCommonConfig {
    @Bean
    public AbstractViewLoader chuckViewLoader() {
        return new ChuckViewLoader();
    }

}
