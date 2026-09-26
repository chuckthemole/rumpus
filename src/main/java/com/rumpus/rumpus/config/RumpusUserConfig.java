package com.rumpus.rumpus.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Config.Security.SecurityConfig;
import com.rumpus.common.Config.User.AbstractCommonUserConfig;
import com.rumpus.common.Dao.User.IAuthorityDao;
import com.rumpus.common.Dao.User.IUserAuthorityDao;
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
import com.rumpus.shared.config.UserConfig;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
@Import({
        SecurityConfig.class,
        DatabaseConfig.class
})
public class RumpusUserConfig
        extends
            AbstractCommonUserConfig<RumpusUser, RumpusUserMetaData, IRumpusUserService, IRumpusUserDao, RumpusUserFactory> {

    public static final String BEAN_RUMPUS_USER_SERVICE = "rumpusUserService";
    public static final String BEAN_RUMPUS_USER_DAO = "rumpusUserDao";
    public static final String BEAN_RUMPUS_USER_FACTORY = "rumpusUserFactory";
    public static final String BEAN_RUMPUS_USER_SECURITY_SERVICE = "rumpusUserSecurityService";
    public static final String BEAN_RUMPUS_ADMIN_USER_VIEW = "rumpusAdminUserView";

    @Bean
    @DependsOn({SecurityConfig.BEAN_JDBC_USER_DETAILS_MANAGER})
    public UserSecurityService rumpusUserSecurityService(
            JdbcUserDetailsManager jdbcUserDetailsManager) {
        return new UserSecurityService(jdbcUserDetailsManager);
    }

    @Bean
    @DependsOn({DatabaseConfig.BEAN_DATA_SOURCE})
    public IRumpusUserDao rumpusUserDao(DataSource dataSource) {
        IRumpusUserDao userDao = new RumpusUserDao(dataSource);
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
    @DependsOn({BEAN_RUMPUS_USER_DAO, UserConfig.BEAN_USER_AUTHORITY_DAO})
    public AuthenticationManager authenticationManager(
            IRumpusUserDao rumpusUserDao,
            IUserAuthorityDao rumpusUserAuthorityDao,
            IAuthorityDao rumpusAuthorityDao) {
        return new RumpusUserAuthenticationManager(
                rumpusUserDao,
                rumpusUserAuthorityDao,
                rumpusAuthorityDao);
    }

    @Override
    @DependsOn({
            BEAN_RUMPUS_USER_DAO,
            BEAN_RUMPUS_USER_SECURITY_SERVICE,
            BEAN_RUMPUS_USER_FACTORY,
            SecurityConfig.BEAN_PASSWORD_ENCODER})
    protected IRumpusUserService createUserService(
            IRumpusUserDao rumpusUserDao,
            IUserAuthorityDao rumpusUserAuthorityDao,
            IAuthorityDao rumpusAuthorityDao,
            UserSecurityService rumpusUserSecurityService,
            RumpusUserFactory rumpusUserFactory,
            PasswordEncoder passwordEncoder) {
        return new RumpusUserService(
                rumpusUserDao,
                rumpusUserAuthorityDao,
                rumpusAuthorityDao,
                rumpusUserSecurityService,
                rumpusUserFactory,
                passwordEncoder);
    }

    /**
     * TODO: fix
     *
     * This seems redundant, because I have the createUserService() method above,
     * but I think this is necessary to make the bean available for
     * RumpusAdminUserRestController, which is a Spring component that needs to
     * inject the IRumpusUserService bean. The createUserService() method is
     * protected and not a Spring bean, so I need this public method to expose the
     * IRumpusUserService as a Spring bean.
     */
    @Bean
    public IRumpusUserService rumpusUserService(
            IRumpusUserDao dao,
            IUserAuthorityDao authorityDao,
            IAuthorityDao rumpusAuthorityDao,
            UserSecurityService securityService,
            RumpusUserFactory factory,
            PasswordEncoder encoder) {

        return new RumpusUserService(
                dao,
                authorityDao,
                rumpusAuthorityDao,
                securityService,
                factory,
                encoder);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
