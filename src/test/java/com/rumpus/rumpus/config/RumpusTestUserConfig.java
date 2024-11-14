package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;

import com.rumpus.common.Config.AbstractCommonUserConfig;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.RumpusUserAuthenticationManager;
import com.rumpus.rumpus.service.RumpusUserService;
import com.rumpus.rumpus.views.RumpusAdminUserView;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
public class RumpusTestUserConfig extends AbstractCommonUserConfig<RumpusUser, RumpusUserMetaData, IRumpusUserService> {

    @Autowired
    public RumpusTestUserConfig(Environment environment) {
        super(environment);
    }

    @Bean
    public IRumpusUserDao rumpusUserDao() {
        IRumpusUserDao userDao = new RumpusUserDao(this.jdbcUserDetailsManager());
        return userDao;
    }

    @Bean
    @Primary
    public RumpusAdminUserView rumpusAdminUserView() {
        return RumpusAdminUserView.createWithEmptyUser();
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

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
