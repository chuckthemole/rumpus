package com.rumpus.rumpus.config;

import org.python.util.PythonInterpreter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.LogManager;
import com.rumpus.common.Log.LogManagerLoader;
import com.rumpus.common.Python.CommonPython;
import com.rumpus.common.Python.PycommonServer;
import com.rumpus.common.Server.AbstractServer;
import com.rumpus.common.Server.ServerManager;
import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.common.views.Template.AbstractUserTemplate;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.rumpus.rumpus.Rumpus;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;
import com.rumpus.rumpus.service.RumpusUserAuthenticationManager;
import com.rumpus.rumpus.service.RumpusUserService;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusConfig extends AbstractCommonConfig { // AbstractHttpSessionApplicationInitializer

    // @Bean
    // public IRumpusUserDao rumpusUserDao() {
    //     IRumpusUserDao userDao = new RumpusUserDao(this.jdbcUserDetailsManager());
    //     return userDao;
    // }

    // @Bean
    // @Primary
    // public RumpusAdminUserView rumpusAdminUserView() {
    //     return RumpusAdminUserView.create(RumpusUser.create("EMPTY_USERNAME", "EMPTY_PASSWORD", "EMPTY_EMAIL"));
    // }

    // @Bean
    // @DependsOn({"rumpusUserDao"})
    // public AbstractUserService<RumpusUser, RumpusUserMetaData> rumpusUserService() {
    //     return new RumpusUserService(this.rumpusUserDao());
    // }

    // @Bean
    // public AbstractUserTemplate<RumpusUser, RumpusUserMetaData> rumpusUserTemplate() {
    //     return RumpusAdminUserView.create(RumpusUser.createEmptyUser());
    // }

    // @Bean
    // @DependsOn({"rumpusUserDao"})
    // public AuthenticationManager authenticationManager() {
    //     return new RumpusUserAuthenticationManager(this.rumpusUserDao());
    // }

    @Bean
    public LogManager logManager() {
        LogManager manager = LogManagerLoader.getDefaultLogManager();
        return manager;
    }

    // @Bean
    // @DependsOn({"rumpusUserDao"})
    // public RumpusLoader rumpusLoader() {
    //     return new RumpusLoader(rumpusUserDao());
    // }

    @Bean
    public ForumThreadManager forumThreadManager() {
        ForumThreadManager manager = ForumThreadManager.create();
        for(ForumThread forumThread : Rumpus.rumpusForumThreads) {
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
