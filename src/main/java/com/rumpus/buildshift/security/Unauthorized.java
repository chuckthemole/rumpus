package com.rumpus.buildshift.security;

import com.rumpus.common.Security.Authentication.AbstractAuthenticationEntryPoint;

import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO: this seems like it could be in common
 */
public class Unauthorized extends AbstractAuthenticationEntryPoint {

    @Override
    protected void handleAuthException(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException) throws IOException {
            // Respond with a 401 Unauthorized when access is denied
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}