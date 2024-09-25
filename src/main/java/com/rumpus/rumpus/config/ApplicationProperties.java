package com.rumpus.rumpus.config;

import com.rumpus.common.Config.Properties.AbstractYamlApplicationProperties;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties extends AbstractYamlApplicationProperties {

    private static final String NAME = "ApplicationProperties";

    public ApplicationProperties() {
        super(NAME);
    }
}
