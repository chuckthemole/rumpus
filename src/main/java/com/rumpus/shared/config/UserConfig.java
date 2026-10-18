package com.rumpus.shared.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.User.AuthRolesConfig;
import com.rumpus.common.Config.User.UserAuthRolesConfig;
import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Config.User.Security.IdResolverConfig;

@Configuration
@Import({
        DatabaseConfig.class,
        AuthRolesConfig.class,
        UserAuthRolesConfig.class,
        IdResolverConfig.class
})
public class UserConfig {

}
