package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;

import com.rumpus.common.Config.AbstractCommonUserConfig;
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
public class RumpusUserConfig extends AbstractCommonUserConfig<RumpusUser, RumpusUserMetaData, IRumpusUserService> {

    public static final String NAME = "RumpusUserConfig";
    
    @Autowired
    public RumpusUserConfig(Environment environment) {
        super(NAME, environment);
    }

    @Bean
    public IRumpusUserDao rumpusUserDao() {
        IRumpusUserDao userDao = new RumpusUserDao(this.jdbcUserDetailsManager());
        return userDao;
    }

    @Bean
    @Primary
    public RumpusAdminUserView rumpusAdminUserView() {
        return RumpusAdminUserView.createWithUser(RumpusUser.createEmptyUser());
    }

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
