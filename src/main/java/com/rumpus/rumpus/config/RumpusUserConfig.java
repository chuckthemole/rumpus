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
import com.rumpus.common.Service.User.UserSecurityService;
import com.rumpus.rumpus.data.User.IRumpusUserDao;
import com.rumpus.rumpus.data.User.RumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserFactory;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.RumpusUserAuthenticationManager;
import com.rumpus.rumpus.service.RumpusUserService;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
public class RumpusUserConfig
        extends
            AbstractCommonUserConfig<RumpusUser, RumpusUserMetaData, IRumpusUserService> {

    @Autowired
    public RumpusUserConfig(Environment environment) {
        super(environment);
    }

    @Bean
    public UserSecurityService rumpusUserSecurityService() {
        return new UserSecurityService(this.jdbcUserDetailsManager());
    }

    @Bean
    public IRumpusUserDao rumpusUserDao() {
        IRumpusUserDao userDao = new RumpusUserDao(this.dataSource());
        return userDao;
    }

    @Bean
    public RumpusUserFactory rumpusUserFactory() {
        return new RumpusUserFactory();
    }

    @Bean
    @Primary
    public RumpusAdminUserView rumpusAdminUserView(RumpusUserFactory rumpusUserFactory) {
        return new RumpusAdminUserView(rumpusUserFactory.createEmpty(), rumpusUserFactory);
    }

    @Bean
    @DependsOn({"rumpusUserDao"})
    public AuthenticationManager authenticationManager() {
        return new RumpusUserAuthenticationManager(this.rumpusUserDao());
    }

    /**
     * TODO: can we add params to be injected here? We would need to alter the
     * parent class to accept these dependencies.
     */
    @Override
    @DependsOn({"rumpusUserDao", "rumpusUserSecurityService", "rumpusUserFactory",
            "passwordEncoder"})
    public IRumpusUserService childUserService() {
        return new RumpusUserService(
                this.rumpusUserDao(),
                this.rumpusUserSecurityService(),
                this.rumpusUserFactory(),
                this.passwordEncoder());
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
