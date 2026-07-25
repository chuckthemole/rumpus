package com.rumpus.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.CommonJdbcHttpSessionConfig;
import com.rumpus.common.Security.Authentication.AuthenticationChecker;

@Configuration
@Import({
        CommonJdbcHttpSessionConfig.class
})
public class SharedConfig {

    @Bean
    public AuthenticationChecker authChecker() {
        return new AuthenticationChecker();
    }
}
