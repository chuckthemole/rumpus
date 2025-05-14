package com.rumpus.rumpus.config;

import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Config.Properties.yaml.AbstractYamlApplicationProperties;

@Configuration
public class ApplicationProperties extends AbstractYamlApplicationProperties {

    public ApplicationProperties() {}

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
