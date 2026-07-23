package com.rumpus.rumpus.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.Config.AbstractCommonUserConfig;
import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Config.Security.SecurityConfig;
import com.rumpus.common.Service.User.UserSecurityService;
import com.rumpus.rumpus.data.User.IRumpusUserDao;
import com.rumpus.rumpus.data.User.RumpusUserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserFactory;
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
@Import({
        DatabaseConfig.class,
        SecurityConfig.class
})
public class RumpusTestUserConfig
        extends
            AbstractCommonUserConfig<RumpusUser, RumpusUserMetaData, IRumpusUserService, IRumpusUserDao, RumpusUserFactory> {

    public RumpusTestUserConfig() {
    }

    @Bean
    public IRumpusUserDao rumpusUserDao(DataSource dataSource) {
        IRumpusUserDao userDao = new RumpusUserDao(dataSource);
        return userDao;
    }

    @Bean
    public RumpusUserFactory userFactory() {
        return new RumpusUserFactory();
    }

    @Bean
    @Primary
    public RumpusAdminUserView rumpusAdminUserView() {
        return new RumpusAdminUserView(this.userFactory().createEmpty(), this.userFactory());
    }

    @Bean
    @DependsOn({"rumpusUserDao"})
    public AuthenticationManager authenticationManager(IRumpusUserDao rumpusUserDao) {
        return new RumpusUserAuthenticationManager(rumpusUserDao);
    }

    @Bean
    @DependsOn({"childUserService"})
    public RumpusLoader rumpusLoader(IRumpusUserService rumpusUserService,
            PasswordEncoder passwordEncoder) {
        return new RumpusLoader(rumpusUserService, passwordEncoder);
    }

    @Bean
    public UserSecurityService rumpusUserSecurityService(
            JdbcUserDetailsManager jdbcUserDetailsManager) {
        return new UserSecurityService(jdbcUserDetailsManager);
    }

    @Override
    public IRumpusUserService childUserService(
            IRumpusUserDao userDao,
            UserSecurityService userSecurityService,
            RumpusUserFactory userFactory,
            PasswordEncoder passwordEncoder) {
        return new RumpusUserService(
                userDao,
                userSecurityService,
                userFactory,
                passwordEncoder);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
