package com.rumpus.rumpus.config;

import org.springframework.stereotype.Component;

import com.rumpus.common.User.ActiveUserStore;

@Component
public class AuthenticationHandlerTestConfig extends AuthenticationHandlerConfig {

    public AuthenticationHandlerTestConfig(ActiveUserStore activeUserStore) {
        super(activeUserStore);
    }
}
