package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rumpus.common.Config.AbstractCommonModelConfig;
import com.rumpus.rumpus.service.RumpusServiceManager;

@Configuration
@ComponentScan("com.rumpus.rumpus")
public class RumpusModelConfig extends AbstractCommonModelConfig<RumpusServiceManager> {

    public static final String NAME = "RumpusModelConfig";
    
    @Autowired
    public RumpusModelConfig(Environment environment) {
        super(NAME, environment);
    }

    @Override
    public RumpusServiceManager childServices() {
        return RumpusServiceManager.create();
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }
    
}
