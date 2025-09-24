package com.rumpus.buildshift.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;

import com.rumpus.common.Config.AbstractCommonUserConfig;
import com.rumpus.buildshift.data.User.IUserDao;
import com.rumpus.buildshift.data.User.UserDao;
import com.rumpus.buildshift.database_loader.BuildShiftLoader;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;
import com.rumpus.buildshift.service.IUserService;
import com.rumpus.buildshift.service.UserAuthenticationManager;
import com.rumpus.buildshift.service.UserService;
import com.rumpus.buildshift.views.AdminUserView;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.buildshift")
public class BuildShiftUserConfig extends AbstractCommonUserConfig<
    User,
    UserMetaData,
    IUserService
> {
    
    @Autowired
    public BuildShiftUserConfig(Environment environment) {
        super(environment);
    }

    @Bean
    public IUserDao buildshiftUserDao() {
        IUserDao userDao = new UserDao(this.jdbcUserDetailsManager());
        return userDao;
    }

    @Bean
    @Primary
    public AdminUserView buildshiftAdminUserView() {
        return AdminUserView.createWithUser(User.createEmptyUser());
    }

    // @Bean
    // public AbstractUserTemplate<User, UserMetaData> rumpusUserTemplate() {
    //     return AdminUserView.create(User.createEmptyUser());
    // }

    @Bean
    @DependsOn({"buildshiftUserDao"})
    public AuthenticationManager buildshiftAuthenticationManager() {
        return new UserAuthenticationManager(this.buildshiftUserDao());
    }

    // @Bean
    // @DependsOn({"buildshiftUserDao"})
    // public BuildShiftLoader buildshiftLoader() {
    //     return new BuildShiftLoader(buildshiftUserDao());
    // }

    @Override
    public IUserService childUserService() {
        return new UserService(this.buildshiftUserDao());
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
