package com.rumpus.shared.controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.AbstractCommonRestController;
import com.rumpus.common.Security.Authentication.AuthenticationChecker;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationChecker authChecker;

    @GetMapping("/is_authenticated")
    public ResponseEntity<?> check() {
        return authChecker.isAuthenticated();
    }
}
