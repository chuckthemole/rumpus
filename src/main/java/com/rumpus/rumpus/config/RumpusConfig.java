package com.rumpus.rumpus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.rumpus.common.IApiDB;
import com.rumpus.common.views.IViewLoader;
import com.rumpus.common.ApiDBJdbc;
import com.rumpus.common.ApiDBJdbcUsers;
import com.rumpus.common.CommonConfig;
import com.rumpus.rumpus.data.AuthDao;
import com.rumpus.rumpus.data.IAuthDao;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.models.Auth;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.RumpusUserService;
import com.rumpus.rumpus.views.IRumpusViewLoader;
import com.rumpus.rumpus.views.RumpusViewLoader;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusConfig extends CommonConfig { // AbstractHttpSessionApplicationInitializer

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
        IRumpusUserDao userDao = new RumpusUserDao();
        // ApiDBJdbcUsers<RumpusUser> userApiDBJdbc = new ApiDBJdbcUsers<>(jdbcUserDetailsManager(), userDao.getTable(), userDao.getMapper());
        // Map<String, String> queries = Map.of(CREATE_USER, SET_USERS_QUERY);
        // userApiDBJdbc.setQueriesFromMap(queries);
        IApiDB<RumpusUser> userApiDB = new ApiDBJdbcUsers<>(this.jdbcUserDetailsManager(), userDao.getTable(), userDao.getMapper());
        userDao.setApiDB(userApiDB);
        return userDao;
    }

    @Bean
    public IAuthDao rumpusAuthDao() {
        IAuthDao authDao = new AuthDao();
        IApiDB<Auth> authApiDB = new ApiDBJdbc<>(this.dataSource(), authDao.getTable(), authDao.getMapper());
        authDao.setApiDB(authApiDB);
        return authDao;
    }

    @Bean
    public IRumpusUserService rumpusUserService() {
        return new RumpusUserService(this.rumpusUserDao());
    }

    // @Bean
    // @DependsOn({"rumpusUserDao", "rumpusAuthDao"})
    // RumpusDaoManager rumpusDaoManager() {
    //     // List<RumpusDao<?>> list = new ArrayList<>();
    //     // list.add(rumpusUserDao());
    //     // list.add(rumpusAuthDao());
    //     return new RumpusDaoManager(userDao(), rumpusAuthDao());
    // }

    // @Bean
    // @DependsOn({"rumpusDaoManager"})
    // @ConditionalOnProperty(prefix = "database", name = "loader", havingValue = "true")
    // public RumpusLoader rumpusLoader() {
    //     return new RumpusLoader(userDao(), rumpusAuthDao());
    // }
}
