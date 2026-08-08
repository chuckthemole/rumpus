package com.rumpus.shared.config.SuccessFailureHandlers;

import com.rumpus.common.Config.SuccessFailureHandler.OAuth2.OAuth2HandlerProperties;
import com.rumpus.common.Config.SuccessFailureHandler.OAuth2.OAuth2SuccessHandler;
import com.rumpus.common.Service.JwtService;

public class OAuth2Success extends OAuth2SuccessHandler {

    private final static JwtService jwtService = new JwtService();

    private OAuth2Success(OAuth2HandlerProperties oAuth2HandlerProperties) {
        super(jwtService, oAuth2HandlerProperties);
    }

    public static OAuth2Success create(OAuth2HandlerProperties oAuth2HandlerProperties) {
        return new OAuth2Success(oAuth2HandlerProperties);
    }
}
