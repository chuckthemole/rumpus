package com.rumpus.shared.config.SuccessFailureHandlers;

import com.rumpus.common.Config.SuccessFailureHandler.OAuth2.OAuth2FailureHandler;
import com.rumpus.common.Config.SuccessFailureHandler.OAuth2.OAuth2HandlerProperties;

public class OAuth2Failure extends OAuth2FailureHandler {

    private OAuth2Failure(OAuth2HandlerProperties oAuth2HandlerProperties) {
        super(oAuth2HandlerProperties.getFailureRedirectUrl());
    }

    public static OAuth2Failure create(OAuth2HandlerProperties oAuth2HandlerProperties) {
        return new OAuth2Failure(oAuth2HandlerProperties);
    }
}
