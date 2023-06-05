package com.rumpus.rumpus.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.rumpus.common.ActiveUserStore;
import com.rumpus.common.CommonController;
import com.rumpus.common.util.Pair;
import com.rumpus.common.views.IViewLoader;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.ui.RumpusView;
import com.rumpus.rumpus.views.IRumpusViewLoader;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
abstract class RumpusController extends CommonController {

    private static final String NAME = "RumpusController";

    // Paths
    protected static final String PATH_INDEX = "/";
    protected static final String PATH_API = "/api";
    protected static final String PATH_USER = "/user";
    protected static final String PATH_VIEW = "/view";
    protected static final String PATH_REDIRECT = "redirect:/";
    protected static final String PATH_LOGOUT = "/logout";
    protected static final String PATH_LOGIN = "/login";
    protected static final String PATH_ADMIN = "/admin";

    // Paths for User
    protected static final String PATH_GET_USERS = "/users";
    protected static final String PATH_DELETE_USER = "/delete_user";
    protected static final String PATH_UPDATE_USER = "/update_user";
    protected static final String PATH_VALUE_GET_BY_USER_NAME = "/get_user/{username}";
    protected static final String PATH_VARIABLE_GET_BY_USER_NAME = "username";

    // Models
    protected static final String MODEL_USER = "user";

    // Templates
    protected static final String TEMPLATE_INDEX = "index";
    protected static final String TEMPLATE_ADMIN = "admin";

    @Autowired protected RumpusView view;
    // @Autowired protected IUserService rumpusUserService;
    @Autowired protected IRumpusUserService rumpusUserService;
    @Autowired protected IRumpusViewLoader viewLoader;
    // @Autowired protected JdbcUserDetailsManager userManager;
    @Autowired protected ActiveUserStore activeUserStore;
    @Autowired protected Gson gson;

    public RumpusController() {super(NAME);}
    public RumpusController(String name) {
        super(name);
    }

    protected void currentUserLogin(RumpusUser user, HttpServletRequest request) {
        String password = user.getUserPassword();
        String username = user.getUsername();
        try {
            StringBuilder sbLogInfo = new StringBuilder();
            sbLogInfo.append("\nUser log in info:\n")
                .append("  User Name: ")
                .append(username).append("\n")
                .append("  User Password: ")
                .append(password)
                .append("\n");
            LOG.info(sbLogInfo.toString());
            request.login(username, password);
        } catch (ServletException exception) {
            StringBuilder sbLogInfo = new StringBuilder();
            sbLogInfo.append("\nError with log in request:\n").append("  ").append(exception.toString()).append("\n");
            LOG.info(sbLogInfo.toString());
        }
    }

    protected int debugUser(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n* * User * * \n");
        sb.append("  User name: ").append(user.getUsername()).append("\n");
        sb.append("  User email: ").append(user.getEmail()).append("\n");
        sb.append("  User password: ").append(user.getPassword()).append("\n");
        LOG.info(sb.toString());
        return SUCCESS;
    }
}
