package com.rumpus.rumpus.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Dao.IApiDB;
import com.rumpus.common.Dao.jdbc.ApiDBJdbcUsers;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.LogItem;
import com.rumpus.common.Log.LogManager;
import com.rumpus.common.Log.LogManagerLoader;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.rumpus.rumpus.Rumpus;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.RumpusUserAuthenticationManager;
import com.rumpus.rumpus.service.RumpusUserService;
import com.rumpus.rumpus.views.IRumpusViewLoader;
import com.rumpus.rumpus.views.RumpusViewLoader;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusConfig extends AbstractCommonConfig { // AbstractHttpSessionApplicationInitializer

    // @Bean
    // public RumpusView view() {
    //     return new RumpusView();
    // }

    @Bean
    public IRumpusViewLoader viewLoader() {
        return new RumpusViewLoader();
    }

    @Bean
    public IRumpusUserDao rumpusUserDao() {
        IRumpusUserDao userDao = new RumpusUserDao(this.jdbcUserDetailsManager());
        // ApiDBJdbcUsers<RumpusUser> userApiDBJdbc = new ApiDBJdbcUsers<>(jdbcUserDetailsManager(), userDao.getTable(), userDao.getMapper());
        // Map<String, String> queries = Map.of(CREATE_USER, SET_USERS_QUERY);
        // userApiDBJdbc.setQueriesFromMap(queries);
        // IApiDB<RumpusUser> userApiDB = new ApiDBJdbcUsers<>(this.jdbcUserDetailsManager(), userDao.getTable(), userDao.getMapper());
        // userDao.setApiDB(userApiDB);
        return userDao;
    }

    @Bean
    @DependsOn({"rumpusUserDao"})
    public IRumpusUserService rumpusUserService() {
        return new RumpusUserService(this.rumpusUserDao());
    }

    @Bean
    @DependsOn({"rumpusUserDao"})
    public AuthenticationManager authenticationManager() {
        return new RumpusUserAuthenticationManager(this.rumpusUserDao());
    }

    @Bean
    public LogManager logManager() {
        LogManager manager = LogManagerLoader.getDefaultLogManager();
        return manager;
    }

    @Bean
    @DependsOn({"rumpusUserDao"})
    public RumpusLoader rumpusLoader() {
        return new RumpusLoader(rumpusUserDao());
    }

    @Bean
    public ForumThreadManager forumThreadManager() {
        ForumThreadManager manager = ForumThreadManager.create();
        for(ForumThread forumThread : Rumpus.rumpusForumThreads) {
            manager.put(forumThread.getPageID(), forumThread);
        }
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
