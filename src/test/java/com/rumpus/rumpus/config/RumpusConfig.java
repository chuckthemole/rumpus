package com.rumpus.rumpus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.rumpus.common.Dao.IApiDB;
import com.rumpus.common.views.IViewLoader;
import com.rumpus.common.Dao.jdbc.ApiDBJdbc;
import com.rumpus.common.Dao.jdbc.ApiDBJdbcUsers;
import com.rumpus.common.CommonConfig;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.data.RumpusUserDao;
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
        IApiDB<RumpusUser> userApiDB = new ApiDBJdbcUsers<>(jdbcUserDetailsManager(), userDao.getTable(), userDao.getMapper());
        userDao.setApiDB(userApiDB);
        return userDao;
    }

    @Bean
    public IRumpusUserService rumpusUserService() {
        return new RumpusUserService(rumpusUserDao());
    }

    // @Bean
    // @DependsOn({"rumpusDaoManager"})
    // @ConditionalOnProperty(prefix = "database", name = "loader", havingValue = "true")
    // public RumpusLoader rumpusLoader() {
    //     return new RumpusLoader(userDao(), rumpusAuthDao());
    // }
}
