package com.rumpus.buildshift.config;

import java.util.Map;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Integrations.*;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

@Configuration
@ComponentScan(basePackages = { "com.rumpus.buildshift" })
public class BuildShiftConfig extends AbstractCommonConfig {

    private static final String NOTION_PROJECT_MANAGEMENT_TOKEN = "properties.notion.token.project-management";
    private static final String NOTION_CONSOLE_TOKEN = "properties.notion.token.console";
    private static final String NAVBAR_BRAND = "properties.views.brand";

    private NotionIntegrationRegistry notionRegistry;

    /**
     * TODO: We should move this member to AbstractCommonConfig. It would be nice to
     * have this available for all configs.
     */
    private final StringBuilder postConstructDebug = new StringBuilder();

    @Autowired
    public BuildShiftConfig(Environment environment) {
        super(environment);
    }

    @Bean
    public String navbarBrand() {
        return this.environment.getProperty(NAVBAR_BRAND);
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Bean
    public Map<String, NotionIntegration> projectManagementNotionIntegration() {
        NotionIntegration consoleIntegration = new NotionIntegration(
                this.environment.getProperty(NOTION_CONSOLE_TOKEN));
        NotionIntegration projectManagementIntegration = new NotionIntegration(
                this.environment.getProperty(NOTION_PROJECT_MANAGEMENT_TOKEN));
        return Map.of(
                "consoleIntegration", consoleIntegration,
                "projectManagementIntegration", projectManagementIntegration);
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public NotionIntegrationRegistry notionIntegrationKeyValue() {
        NotionIntegrationRegistry registry = new NotionIntegrationRegistry();
        NotionIntegrationLoader.load(
                this.environment,
                registry,
                AbstractCommonConfig.NOTION_DATABASES,
                NotionResourceType.DATABASE,
                postConstructDebug
        );
        this.notionRegistry = registry;
        return registry;
    }

    /**
     * PostConstruct runs after all beans in this config are created.
     * Ideal place to log diagnostic output collected during bean creation.
     * TODO: We should move this member to AbstractCommonConfig. It would be nice to
     * have this available for all configs.
     */
    @PostConstruct
    public void logPostConstructStatus() {
        LOG(BuildShiftConfig.class, LogLevel.INFO, "===== BuildShiftConfig PostConstruct =====");
        LOG(BuildShiftConfig.class, LogLevel.INFO, postConstructDebug.toString());
        LOG(BuildShiftConfig.class, LogLevel.INFO,
                "Loaded Notion registry with " + (notionRegistry != null ? notionRegistry.size() : 0) + " entries.");
    }

    @Override
    public String toString() {
        return "BuildShiftConfig{" +
                "sqlDialect='" + sqlDialect() + '\'' +
                ", notionRegistrySize=" + (notionRegistry != null ? notionRegistry.size() : 0) +
                '}';
    }
}
