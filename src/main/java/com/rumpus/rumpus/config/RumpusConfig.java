package com.rumpus.rumpus.config;

import org.python.util.PythonInterpreter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.LogManager;
import com.rumpus.common.Log.LogManagerLoader;
import com.rumpus.common.Python.CommonPython;
import com.rumpus.common.Python.PycommonServer;
import com.rumpus.common.Server.AbstractServer;
import com.rumpus.common.Server.ServerManager;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.rumpus.rumpus.IRumpus;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusConfig extends AbstractCommonConfig { // AbstractHttpSessionApplicationInitializer

    @Bean
    public LogManager logManager() {
        LogManager manager = LogManagerLoader.getDefaultLogManager();
        return manager;
    }

    @Bean
    public ForumThreadManager forumThreadManager() {
        ForumThreadManager manager = ForumThreadManager.create();
        for(ForumThread forumThread : IRumpus.rumpusForumThreads) {
            manager.put(forumThread.getPageID(), forumThread);
        }
        return manager;
    }

    @Bean
    public PythonInterpreter pythonInterpreter() {
        return CommonPython.getInterpreter();
    }

    @Bean
    public AbstractServer pycommonServer() {
        return PycommonServer.createAndDoNotStartServer();
    }

    @Bean
    @DependsOn({"pycommonServer"})
    public ServerManager serverManager() {
        ServerManager manager = ServerManager.create();
        manager.addServer("PycommonServer", pycommonServer());
        return manager;
    }

    // @Bean
    // public ForumThread forumThread() {
    //     ForumThread thread = ForumThread.createEmpty();
    //     return ForumThread.createEmpty();
    // }

    // @Bean
    // public List<LogItem> logItems() {
    //     return new ArrayList<>();
    // }

    // @Bean
    // public ProviderManager providerManager() {
    //     List<AuthenticationProvider> providers = new ArrayList<>();
    //     return new ProviderManager(providers);
    // }

    // @Bean
    // public RumpusUserTypeAdapter rumpusUserTypeAdapter() {
    //     return new RumpusUserTypeAdapter();
    // }

    public BeanSerializer beanSerializer() {
        BeanSerializer serializer = new BeanSerializer(null, null, null, null);
        return serializer;
    }

    // @Bean
    // @DependsOn({"rumpusDaoManager"})
    // @ConditionalOnProperty(prefix = "database", name = "loader", havingValue = "true")
    // public RumpusLoader rumpusLoader() {
    //     return new RumpusLoader(userDao(), rumpusAuthDao());
    // }
}
