package com.rumpus.buildshift.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import jakarta.annotation.PostConstruct;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Config.Integration.Notion.NotionProperties;
import com.rumpus.common.Config.Logging.LoggingConfig;
import com.rumpus.common.Config.Security.SecurityConfig;
import com.rumpus.common.Config.Views.ViewsConfig;
import com.rumpus.common.Integrations.NotionIntegration;
import com.rumpus.common.Integrations.NotionIntegrationLoader;
import com.rumpus.common.Integrations.NotionIntegrationRegistry;
import com.rumpus.common.Integrations.NotionResourceType;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

@Configuration
@ComponentScan(basePackages = {"com.rumpus.buildshift"})
@Import({
        LoggingConfig.class,
        SecurityConfig.class,
        DatabaseConfig.class,
        ViewsConfig.class
})
public class BuildShiftConfig extends AbstractCommonObject {

    private NotionIntegrationRegistry notionRegistry;

    /**
     * TODO: We should move this member to AbstractCommonConfig. It would be nice to
     * have this available for all configs.
     */
    private final StringBuilder postConstructDebug = new StringBuilder();

    public BuildShiftConfig() {
    }

    @Bean
    public Map<String, NotionIntegration> projectManagementNotionIntegration(
            NotionProperties notionProperties) {

        return Map.of(
                "consoleIntegration",
                new NotionIntegration(notionProperties.getToken().getConsole()),

                "projectManagementIntegration",
                new NotionIntegration(notionProperties.getToken().getProjectManagement()));
    }

    @Bean
    @Scope("singleton")
    public NotionIntegrationRegistry notionIntegrationKeyValue(NotionProperties notionProperties) {

        NotionIntegrationRegistry registry = new NotionIntegrationRegistry();

        NotionIntegrationLoader.load(
                notionProperties.getDatabases(),
                registry,
                NotionResourceType.DATABASE,
                postConstructDebug);
        this.notionRegistry = registry;
        return registry;
    }

    /**
     * PostConstruct runs after all beans in this config are created. Ideal place to
     * log diagnostic output collected during bean creation. TODO: We should move
     * this member to AbstractCommonConfig. It would be nice to have this available
     * for all configs.
     */
    @PostConstruct
    public void logPostConstructStatus() {
        LOG(BuildShiftConfig.class, LogLevel.INFO, "===== BuildShiftConfig PostConstruct =====");
        LOG(BuildShiftConfig.class, LogLevel.INFO, postConstructDebug.toString());
        LOG(BuildShiftConfig.class, LogLevel.INFO,
                "Loaded Notion registry with "
                        + (notionRegistry != null ? notionRegistry.size() : 0) + " entries.");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
