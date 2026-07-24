package com.rumpus.buildshift.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.Documentation.OpenAPI.ApiGroup;
import com.rumpus.common.Config.Documentation.OpenAPI.OpenApiConfig;

@Configuration
@Import({
        OpenApiConfig.class
})
public class BuildShiftOpenApiConfig {
    @Bean
    ApiGroup bsNotionApi() {
        return new ApiGroup("notion", "/notion-api/**");
    }

    @Bean
    ApiGroup bsViewsApi() {
        return new ApiGroup("views", "/view_bs/**");
    }
}
