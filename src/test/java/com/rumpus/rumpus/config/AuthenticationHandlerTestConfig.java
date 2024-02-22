package com.rumpus.rumpus.config;

import org.springframework.stereotype.Component;

import com.rumpus.common.User.ActiveUserStore;
import com.rumpus.common.User.AuthenticationHandler;

@Component
public class AuthenticationHandlerTestConfig extends AuthenticationHandler {

    public AuthenticationHandlerTestConfig(ActiveUserStore activeUserStore) {
        super(activeUserStore);
    }
}
