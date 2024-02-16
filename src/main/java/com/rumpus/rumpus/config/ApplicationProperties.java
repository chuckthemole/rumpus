package com.rumpus.rumpus.config;

@org.springframework.context.annotation.Configuration
public class ApplicationProperties extends com.rumpus.common.Config.Properties.AbstractYamlApplicationProperties {

    private static final String NAME = "ApplicationProperties";

    public ApplicationProperties() {
        super(NAME);
    }
}
