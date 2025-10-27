package com.rumpus.buildshift.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Config.AbstractOpenApiConfig;
import com.rumpus.common.Config.AbstractOpenApiConfig.ApiGroup;

@Configuration
public class OpenApiConfigBS extends AbstractOpenApiConfig {

    /**
     * Define the API groups for this application.
     * These will be used to create GroupedOpenApi beans.
     */
    @Override
    protected List<ApiGroup> initApiGroups() {
        return List.of(
                new ApiGroup("notion", "/notion-api/**"),
                new ApiGroup("views", "/view_bs/**"));
    }

    @Override
    protected String getTitle() {
        return "Buildshift SpringBoot API";
    }

    @Override
    protected String getDescription() {
        return "API documentation for Builshift services.";
    }

    @Override
    protected String getVersion() {
        return "v1.0";
    }
}
