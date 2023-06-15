package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rumpus.common.User.ActiveUserStore;
import com.rumpus.common.User.AuthenticationHandler;

@Component
public class AuthenticationHandlerConfig extends AuthenticationHandler {

    @Autowired
    public AuthenticationHandlerConfig(ActiveUserStore activeUserStore) {
        super(activeUserStore);
    }
}
