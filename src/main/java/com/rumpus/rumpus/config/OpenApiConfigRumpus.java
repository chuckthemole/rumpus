package com.rumpus.rumpus.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Config.AbstractOpenApiConfig;
import com.rumpus.common.Config.AbstractOpenApiConfig.ApiGroup;

@Configuration
public class OpenApiConfigRumpus extends AbstractOpenApiConfig {

    /**
     * Define the API groups for this application.
     * These will be used to create GroupedOpenApi beans.
     */
    @Override
    protected List<ApiGroup> initApiGroups() {
        return List.of(
                new ApiGroup("public", "/api/public/**"),
                new ApiGroup("admin", "/api/admin/**"),
                new ApiGroup("experimental", "/api/experimental/**"));
    }

    @Override
    protected String getTitle() {
        return "MyApp API";
    }

    @Override
    protected String getDescription() {
        return "API documentation for MyApp services.";
    }

    @Override
    protected String getVersion() {
        return "v1.2";
    }
}
