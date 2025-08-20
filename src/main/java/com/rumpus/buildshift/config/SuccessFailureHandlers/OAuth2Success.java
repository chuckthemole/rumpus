package com.rumpus.buildshift.config.SuccessFailureHandlers;

import com.rumpus.common.Config.SuccessFailureHandler.OAuth2.AbstractOAuth2SuccesHandler;
import com.rumpus.common.Service.JwtService;

public class OAuth2Success extends AbstractOAuth2SuccesHandler {

    private OAuth2Success() {
        super(new JwtService());
    }

    public static OAuth2Success create() {
        return new OAuth2Success();
    }

    @Override
    public String getBaseRedirectUrl() {
        return "http://localhost:3000/auth/failure"; // TODO: let's put this in env vars, something like REACT_URI
    }

}
