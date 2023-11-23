package com.rumpus.rumpus.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Controller.AbstractCommonController;
import com.rumpus.common.Controller.AbstractCommonRestController;

import jakarta.servlet.http.HttpServletRequest;

// TODO: is_authenticated is not working. it is loading and not returning anything. RupmpusRestController is working and has the same implementation. Try to figure out why this is not working.
@RestController
@RequestMapping(AbstractCommonRestController.COMMON_REST_API_PATH)
public class CommonRestControler extends AbstractCommonRestController {

    private static final String NAME = "Rumpus - CommonRestController";

    @Autowired private AuthenticationManager authenticationManager;
    
    public CommonRestControler() {
        super(NAME);
    }

    @GetMapping(value = "/paths")
    public ResponseEntity<Map<String, String>> getCommonPaths(HttpServletRequest request) {
        LOG.info("CommonRestControler::getCommonPaths()");
        return new ResponseEntity<Map<String, String>>(AbstractCommonController.commonPaths.getBasePath(AbstractCommonController.COMMON_REST_API_PATH), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/paths/{basePath}")
    public ResponseEntity<Map<String, String>> getPathsFromBasePath(@PathVariable("basePath") String basePath, HttpServletRequest request) {
        LOG.info("CommonRestControler::getPathsFromBasePath()");
        Map<String, String> paths = AbstractCommonController.commonPaths.getBasePath(basePath);
        if(paths == null) {
            LogBuilder.logBuilderFromStringArgs("No paths found with basePath=", basePath).info();
            return new ResponseEntity<Map<String, String>>(Map.of(), HttpStatus.NOT_FOUND);
        }
        if(paths.isEmpty()) {
            LogBuilder.logBuilderFromStringArgs("No paths found with basePath=", basePath, " (EMPTY)").info();
        }
        return new ResponseEntity<Map<String, String>>(paths, HttpStatus.ACCEPTED);
    }

    @Override
    @GetMapping(value = "/is_authenticated")
    protected ResponseEntity<Boolean> getAuthenticationOfUser(Authentication authentication) {
        LOG.info("CommonRestControler::getAuthenticationOfUser()");
        if(authentication == null) {
            LOG.info("CommonRestControler::getAuthenticationOfUser() - authentication is null");
            return new ResponseEntity<Boolean>(false, HttpStatus.ACCEPTED);
        }
        Authentication auth = authenticationManager.authenticate(authentication);
        boolean isAuthenticated = false;
        if(auth != null) {
            isAuthenticated = auth.isAuthenticated();
        } else {
            LOG.info("CommonRestControler::getAuthenticationOfUser() - auth is null");
        }
        return new ResponseEntity<Boolean>(isAuthenticated, HttpStatus.ACCEPTED);
    }
}
