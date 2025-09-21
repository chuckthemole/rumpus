package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.LogItem.LogItemCollectionManager;
import com.rumpus.common.Python.CommonPython;
import com.rumpus.common.Python.PycommonServer;
import com.rumpus.common.Server.AbstractServer;
import com.rumpus.common.Server.ServerManager;
import com.rumpus.common.Service.JwtService;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.rumpus.rumpus.IRumpus;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan(basePackages = {"com.rumpus.rumpus"})
public class RumpusConfig extends AbstractCommonConfig { // AbstractHttpSessionApplicationInitializer

    @Autowired
    public RumpusConfig(Environment environment) {
        super(environment);
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

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    // @Bean
    // @DependsOn({"rumpusDaoManager"})
    // @ConditionalOnProperty(prefix = "database", name = "loader", havingValue = "true")
    // public RumpusLoader rumpusLoader() {
    //     return new RumpusLoader(userDao(), rumpusAuthDao());
    // }
}
