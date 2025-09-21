package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.LogItem.LogItemCollectionManager;
import com.rumpus.common.Python.CommonPython;
import com.rumpus.common.Python.PycommonServer;
import com.rumpus.common.Server.AbstractServer;
import com.rumpus.common.Server.ServerManager;
import com.rumpus.common.Service.JwtService;
import com.rumpus.rumpus.IRumpus;

@TestConfiguration
@ComponentScan("com.rumpus.rumpus")
public class RumpusTestConfig extends AbstractCommonConfig {

    @Autowired
    public RumpusTestConfig(Environment environment) {
        super(environment);
    }

    @Bean
    public LogItemCollectionManager logManager() {
        LogItemCollectionManager manager = LogItemCollectionManager.createWithMainAndAdmin();
        return manager;
    }

    @Bean
    public ForumThreadManager forumThreadManager() {
        ForumThreadManager manager = ForumThreadManager.create();
        for (ForumThread forumThread : IRumpus.rumpusForumThreads) {
            manager.put(forumThread.getPageID(), forumThread);
        }
        return manager;
    }

    @Bean
    public AbstractServer pycommonServer() {
        return PycommonServer.createAndDoNotStartServer();
    }

    @Bean
    @DependsOn({ "pycommonServer" })
    public ServerManager serverManager() {
        ServerManager manager = ServerManager.create();
        manager.addServer("PycommonServer", pycommonServer());
        return manager;
    }

    public BeanSerializer beanSerializer() {
        BeanSerializer serializer = new BeanSerializer(null, null, null, null);
        return serializer;
    }

    // TODO:
    // Added this 2023/11/28 to fix the following error:
    // Please ensure Spring Security & Spring MVC are configured in a shared
    // ApplicationContext.
    // Maybe look into a bit more
    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
