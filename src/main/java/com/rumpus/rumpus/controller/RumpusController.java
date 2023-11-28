package com.rumpus.rumpus.controller;

import java.util.List;
import java.util.Map;

import org.python.core.Py;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Server;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.rumpus.common.Controller.AbstractCommonController;
import com.rumpus.common.Controller.ICommonPaths;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.LogItem;
import com.rumpus.common.Log.LogManager;
import com.rumpus.common.Server.ServerManager;
import com.rumpus.common.User.ActiveUserStore;
import com.rumpus.common.views.AbstractViewLoader;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.service.IRumpusUserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public abstract class RumpusController extends AbstractCommonController {

    private static final String NAME = "RumpusController";

    @Autowired protected static Environment environment;

    // TODO  - see what constants we can move to AbstractCommonController
    // Paths
    protected static final String PATH_INDEX = "/";
    protected static final String PATH_API = "/api";
    protected static final String PATH_SERVER_API = "/api/server";
    protected static final String PATH_PYCOMMON_API = "/api/pycommon";
    protected static final String PATH_SCRAPER = "/scraper";
    // protected static final String PATH_SCRAPER_START = "/scraper/start";
    protected static final String PATH_USER = "/user";
    protected static final String PATH_VIEW = "/view";
    protected static final String PATH_REDIRECT = "redirect:/";
    protected static final String PATH_LOGOUT = "/logout";
    protected static final String PATH_LOGIN = "/login";
    protected static final String PATH_ADMIN = "/admin";
    protected static final String PATH_LOG_ACTION = "/log_action";

    // Paths for User
    protected static final String PATH_GET_USERS = "/users";
    protected static final String PATH_DELETE_USER = "/delete_user";
    protected static final String PATH_UPDATE_USER = "/update_user";

    protected static final String PATH_VALUE_GET_BY_USER_NAME = "/get_user_by_name/{username}";
    protected static final String PATH_VALUE_GET_BY_USER_ID = "/user/{id}";
    protected static final String PATH_VARIABLE_GET_BY_USER_NAME = "username";
    protected static final String PATH_VARIABLE_GET_BY_USER_ID = "id";

    protected static final String PATH_POST_USER_TIME_ZONE = "/user_time_zone";

    // Models
    protected static final String MODEL_USER = "user";

    // Templates
    protected static final String TEMPLATE_INDEX = "index";
    protected static final String TEMPLATE_ADMIN = "admin";
    protected static final String TEMPLATE_USER = "user/user";

    // File paths
    protected static final String PYTHON_DIR_PATH = "src/main/python/";

    // Python files
    protected static final String VOICE_ASSISTANT_FILE_PATH = PYTHON_DIR_PATH + "voice_assistant.py";

    // @Autowired protected RumpusView view;
    // @Autowired protected IUserService rumpusUserService;
    // @Autowired protected ProviderManager authManager;
    // @Autowired protected AuthenticationManager authManager;
    @Autowired protected IRumpusUserService rumpusUserService;
    // @Autowired
    // @Qualifier("rumpusViewLoader")
    // protected AbstractViewLoader viewLoader;
    // @Autowired protected JdbcUserDetailsManager userManager;
    @Autowired protected ActiveUserStore activeUserStore;
    @Autowired protected Gson gson;

    @Autowired protected ForumThreadManager forumThreadManager;

    @Autowired protected LogManager logManager;

    @Autowired protected PythonInterpreter pythonInterpreter;

    @Autowired protected ServerManager serverManager;

    protected static String RUMPUS_DEFAULT_BASE_PATH = "/api";

    public RumpusController() {
        super(NAME);
        this.init();
    }
    public RumpusController(String name) {
        super(name);
        this.init();
    }
    private void init() {
        //Common Paths
        Map<String, String> paths = Map.of(
            "CurrentUserInfo", "/current_user",
            "CreateUser", "/create_user"
        );
        AbstractCommonController.commonPaths.addBasePath(RumpusController.RUMPUS_DEFAULT_BASE_PATH, paths, true);
        // this.setCurrentBasePath(RumpusController.RUMPUS_DEFAULT_BASE_PATH);
    }

    protected void currentUserLogin(RumpusUser user, HttpServletRequest request) {
        LOG.info("RumpusController::currentUserLogin()");
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

    protected int debugUser(RumpusUser user) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n* * User * * \n");
        sb.append("  User name: ").append(user.getUsername()).append("\n");
        sb.append("  User email: ").append(user.getEmail()).append("\n");
        sb.append("  User password: ").append(user.getPassword()).append("\n");
        LOG.info(sb.toString());
        return SUCCESS;
    }
}
