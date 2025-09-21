package com.rumpus.buildshift.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Integrations.NotionIntegration;
import com.rumpus.common.Log.LogItem.LogItemCollectionManager;
import com.rumpus.common.Python.CommonPython;
import com.rumpus.common.Python.PycommonServer;
import com.rumpus.common.Server.AbstractServer;
import com.rumpus.common.Server.ServerManager;
import com.rumpus.common.Service.JwtService;
import com.fasterxml.jackson.databind.ser.BeanSerializer;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan(basePackages = { "com.rumpus.buildshift" })
public class BuildShiftConfig extends AbstractCommonConfig { // AbstractHttpSessionApplicationInitializer

    private static final String NOTION_PROJECT_MANAGEMENT_TOKEN = "properties.notion.token.project-management";
    private static final String NOTION_CONSOLE_TOKEN = "properties.notion.token.console";

    @Autowired
    public BuildShiftConfig(Environment environment) {
        super(environment);
    }

    public BeanSerializer beanSerializer() {
        BeanSerializer serializer = new BeanSerializer(null, null, null, null);
        return serializer;
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    @Bean
    public Map<String, NotionIntegration> projectManagementNotionIntegration() {
        NotionIntegration consoleIntegration = new NotionIntegration(
                this.environment.getProperty(NOTION_CONSOLE_TOKEN));
        NotionIntegration projectManagementIntegration = new NotionIntegration(
                this.environment.getProperty(NOTION_PROJECT_MANAGEMENT_TOKEN));
        return Map.of("consoleIntegration", consoleIntegration, "projectManagementIntegration",
                projectManagementIntegration);
    }
}
