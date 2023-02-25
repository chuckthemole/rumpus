package com.rumpus.rumpus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;

import com.rumpus.common.ActiveUserStore;
import com.rumpus.common.CommonController;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.ui.RumpusView;
import com.rumpus.rumpus.views.IViewLoader;

@Controller
abstract class RumpusController extends CommonController {

    private static final String NAME = "RumpusController";
    protected static final String API = "/api";

    @Autowired
    protected RumpusView view;
    @Autowired
    protected IUserService rumpusUserService;
    @Autowired
    protected IViewLoader viewLoader;
    @Autowired
    protected JdbcUserDetailsManager userManager;
    @Autowired
    protected ActiveUserStore activeUserStore;

    public RumpusController() {super(NAME);}
    public RumpusController(String name) {
        super(name);
    }
}
