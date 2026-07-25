package com.rumpus.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.Server.ServerPortConfig;
import com.rumpus.common.Config.Server.ServerConfig;
import com.rumpus.common.Config.Session.CommonJdbcHttpSessionConfig;
import com.rumpus.common.Security.Authentication.AuthenticationChecker;

@Configuration
@Import({
        CommonJdbcHttpSessionConfig.class,
        ServerConfig.class,
        ServerPortConfig.class
})
public class SharedConfig {

    @Bean
    public AuthenticationChecker authChecker() {
        return new AuthenticationChecker();
    }
}
