package com.rumpus.buildshift.config;

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

import com.rumpus.common.Config.AbstractCommonUserConfig;
import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Config.Security.SecurityConfig;
import com.rumpus.common.Service.User.UserSecurityService;
import com.rumpus.buildshift.data.User.IUserDao;
import com.rumpus.buildshift.data.User.UserDao;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserFactory;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;
import com.rumpus.buildshift.service.IUserService;
import com.rumpus.buildshift.service.UserAuthenticationManager;
import com.rumpus.buildshift.service.UserService;
import com.rumpus.buildshift.views.AdminUserView;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.buildshift")
@Import({
        SecurityConfig.class,
        DatabaseConfig.class
})
public class BuildShiftUserConfig
        extends
            AbstractCommonUserConfig<User, UserMetaData, IUserService, IUserDao, UserFactory> {

    public static final String BEAN_BUILD_SHIFT_USER_SERVICE = "buildShiftUserService";
    public static final String BEAN_BUILD_SHIFT_USER_DAO = "buildShiftUserDao";
    public static final String BEAN_BUILD_SHIFT_USER_FACTORY = "buildShiftUserFactory";
    public static final String BEAN_BUILD_SHIFT_USER_SECURITY_SERVICE = "buildShiftUserSecurityService";

    public BuildShiftUserConfig() {
    }

    @Bean
    @DependsOn({DatabaseConfig.BEAN_DATA_SOURCE})
    public IUserDao buildshiftUserDao(DataSource dataSource) {
        IUserDao userDao = new UserDao(dataSource);
        return userDao;
    }

    @Bean
    public UserFactory userFactory() {
        return new UserFactory();
    }

    @Bean
    @Primary
    public AdminUserView buildshiftAdminUserView() {
        return AdminUserView.createWithUser(this.userFactory().createEmpty());
    }

    // @Bean
    // public AbstractUserTemplate<User, UserMetaData> rumpusUserTemplate() {
    // return AdminUserView.create(User.createEmptyUser());
    // }

    @Bean
    @DependsOn({BEAN_BUILD_SHIFT_USER_DAO})
    public AuthenticationManager buildshiftAuthenticationManager(IUserDao userDao) {
        return new UserAuthenticationManager(userDao);
    }

    // @Bean
    // @DependsOn({"buildshiftUserDao"})
    // public BuildShiftLoader buildshiftLoader() {
    // return new BuildShiftLoader(buildshiftUserDao());
    // }

    @Bean
    public UserSecurityService buildshiftUserSecurityService(
            JdbcUserDetailsManager jdbcUserDetailsManager) {
        return new UserSecurityService(jdbcUserDetailsManager);
    }

    @Override
    @DependsOn({
            BEAN_BUILD_SHIFT_USER_DAO,
            BEAN_BUILD_SHIFT_USER_SECURITY_SERVICE,
            BEAN_BUILD_SHIFT_USER_FACTORY,
            SecurityConfig.BEAN_PASSWORD_ENCODER})
    public IUserService childUserService(
            IUserDao userDao,
            UserSecurityService userSecurityService,
            UserFactory userFactory,
            PasswordEncoder passwordEncoder) {
        return new UserService(
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
