package com.rumpus.rumpus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;

import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Config.AbstractCommonUserConfig;
import com.rumpus.common.Log.LogManager;
import com.rumpus.common.Log.LogManagerLoader;
import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.common.views.Template.AbstractUserTemplate;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.RumpusUserAuthenticationManager;
import com.rumpus.rumpus.service.RumpusUserService;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusUserConfig extends AbstractCommonUserConfig<RumpusUser, RumpusUserMetaData, IRumpusUserService> {
    @Bean
    public IRumpusUserDao rumpusUserDao() {
        IRumpusUserDao userDao = new RumpusUserDao(this.jdbcUserDetailsManager());
        return userDao;
    }

    @Bean
    @Primary
    public RumpusAdminUserView rumpusAdminUserView() {
        return RumpusAdminUserView.create(RumpusUser.createEmptyUser());
    }

    // @Bean
    // @DependsOn({"rumpusUserDao"})
    // public AbstractUserService<RumpusUser, RumpusUserMetaData> rumpusUserService() {
    //     return new RumpusUserService(this.rumpusUserDao());
    // }

    // @Bean
    // public AbstractUserTemplate<RumpusUser, RumpusUserMetaData> rumpusUserTemplate() {
    //     return RumpusAdminUserView.create(RumpusUser.createEmptyUser());
    // }

    @Bean
    @DependsOn({"rumpusUserDao"})
    public AuthenticationManager authenticationManager() {
        return new RumpusUserAuthenticationManager(this.rumpusUserDao());
    }

    @Bean
    @DependsOn({"rumpusUserDao"})
    public RumpusLoader rumpusLoader() {
        return new RumpusLoader(rumpusUserDao());
    }

    @Override
    public IRumpusUserService childUserService() {
        return new RumpusUserService(this.rumpusUserDao());
    }    
}
