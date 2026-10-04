package com.rumpus.shared.config;

import com.rumpus.common.Config.User.AuthRolesConfig;
import com.rumpus.common.Config.User.UserAuthRolesConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.Database.DatabaseConfig;

@Configuration
@Import({
        DatabaseConfig.class,
        AuthRolesConfig.class,
        UserAuthRolesConfig.class
})
public class UserConfig {

}
