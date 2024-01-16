package com.rumpus.chuck;

import org.springframework.stereotype.Controller;

import com.rumpus.common.Controller.AbstractCommonController;
import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@Controller
abstract public class ChuckController extends AbstractCommonController
    < // TODO: using rumpus stuff here for now. should change to chuck stuff
        RumpusUser,
        RumpusUserMetaData,
        AbstractUserService<RumpusUser, RumpusUserMetaData>, // TODO: change when I implement IChuckUserService
        RumpusAdminUserView
    > {
    
        private static final String NAME = "ChuckController";

        // Paths for charles pikaart thomas
        protected static final String PATH_CHARLES_PIKAART_THOMAS = "/charles_pikaart_thomas";
        protected static final String PATH_CHARLES_PIKAART_THOMAS_VIEW = "/charles_pikaart_thomas/view";

        // URIs
        protected static final String CHARLES_PIKAART_THOMAS_DEV_URI = "http://127.0.0.1:8000/";
        protected static final String CHARLES_PIKAART_THOMAS_BETA_URI = "http://127.0.0.1:8000/beta/";
        protected static final String CHARLES_PIKAART_THOMAS_LIVE_URI = "http://charles-pikaart-thomas.com/";

        // @Autowired
        // @Qualifier("chuckViewLoader")
        // protected AbstractViewLoader viewLoader;

        public ChuckController() {
            super(NAME);
        }
}
