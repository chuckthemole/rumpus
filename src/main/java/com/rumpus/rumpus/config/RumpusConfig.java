package com.rumpus.rumpus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.Cloud.Aws.AwsConfig;
import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Config.Logging.LoggingConfig;
import com.rumpus.common.Config.Security.SecurityConfig;
import com.rumpus.common.Config.Views.ViewsConfig;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Python.PycommonServer;
import com.rumpus.common.Serializer.ISerializerRegistry;
import com.rumpus.common.Serializer.SerializerRegistry;
import com.rumpus.common.Server.AbstractServer;
import com.rumpus.common.Server.ServerManager;
import com.rumpus.common.Service.JwtService;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.rumpus.rumpus.IRumpus;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserSerializer;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan(basePackages = {"com.rumpus.rumpus"})
@Import({
        LoggingConfig.class,
        SecurityConfig.class,
        DatabaseConfig.class,
        ViewsConfig.class,
        AwsConfig.class
})
public class RumpusConfig { // AbstractHttpSessionApplicationInitializer

    public RumpusConfig() {
    }

    // TODO: DELETE
    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }

    @Bean
    public ForumThreadManager forumThreadManager() {
        ForumThreadManager manager = ForumThreadManager.create();
        for (ForumThread forumThread : IRumpus.rumpusForumThreads) {
            manager.put(forumThread.getPageID(), forumThread);
        }
        return manager;
    }

    /**
     * TODO: I moved this from RumpusModelConfig. I don't want it to break anything.
     * See if we can remove completely.
     *
     * @return
     */
    @Bean
    public ISerializerRegistry createSerializerRegistry() {
        ISerializerRegistry serializerRegistry = SerializerRegistry.create();
        serializerRegistry.registerSerializer(RumpusUser.class,
                RumpusUserSerializer.jsonSerializer());
        return serializerRegistry;
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

    public BeanSerializer beanSerializer() {
        BeanSerializer serializer = new BeanSerializer(null, null, null, null);
        return serializer;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
