package com.rumpus.rumpus.config.SuccessFailureHandlers;

import com.rumpus.common.Config.SuccessFailureHandler.OAuth2.AbstractOAuth2FailureHandler;

public class OAuth2Failure extends AbstractOAuth2FailureHandler {

    public static OAuth2Failure create() {
        return new OAuth2Failure();
    }

    @Override
    public String getBaseRedirectUrl() {
        return "http://localhost:3000/auth/failure"; // TODO: let's put this in env vars, something like REACT_URI
    }
}
