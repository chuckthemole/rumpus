package com.rumpus.shared.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Controller.User.AuthorityRolesController;

@Configuration
@Import({
        AuthorityRolesController.class
})
public class UserControllerConfig {

}
