package com.rumpus.rumpus.controller;

import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rumpus.common.Controller.AbstractCommonController;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.LogManager;
import com.rumpus.common.Server.ServerManager;
import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.common.User.ActiveUserStore;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@Controller
abstract public class AbstractRumpusController extends AbstractCommonController
    <
        RumpusUser,
        RumpusUserMetaData,
        AbstractUserService<RumpusUser, RumpusUserMetaData>,
        RumpusAdminUserView
    > {
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

        // @Autowired protected IRumpusUserService rumpusUserService;

        // @Autowired
        // @Qualifier("rumpusViewLoader")
        // protected AbstractViewLoader viewLoader;
        // @Autowired protected JdbcUserDetailsManager userManager;
        @Autowired protected ActiveUserStore activeUserStore;
        // @Autowired protected Gson gson;

        @Autowired protected ForumThreadManager forumThreadManager;

        @Autowired protected LogManager logManager;

        @Autowired protected PythonInterpreter pythonInterpreter;

        @Autowired protected ServerManager serverManager;

        protected static String RUMPUS_DEFAULT_BASE_PATH = "/api";

        public AbstractRumpusController(String name) {
            super(name);
            this.init();
        }
        
        private void init() {
            // this.setUserController(todo stopped here);

            //Common Paths
            java.util.Map<String, String> paths = java.util.Map.of(
                "CurrentUserInfo", "/current_user",
                "CreateUser", "/create_user"
            );
            AbstractCommonController.commonPaths.addBasePath(AbstractRumpusController.RUMPUS_DEFAULT_BASE_PATH, paths, true);
            // this.setCurrentBasePath(RumpusController.RUMPUS_DEFAULT_BASE_PATH);
        }
}
