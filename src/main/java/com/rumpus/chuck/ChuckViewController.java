package com.rumpus.chuck;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.chuck.views.ChuckViewLoader;
import com.rumpus.common.Controller.AbstractViewController;
import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;
import com.rumpus.rumpus.service.RumpusServiceManager;
import com.rumpus.rumpus.views.RumpusAdminUserView;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping(ChuckController.PATH_CHARLES_PIKAART_THOMAS_VIEW)
@CrossOrigin(origins = {ChuckController.CHARLES_PIKAART_THOMAS_DEV_URI, ChuckController.CHARLES_PIKAART_THOMAS_BETA_URI, ChuckController.CHARLES_PIKAART_THOMAS_LIVE_URI})
public class ChuckViewController extends AbstractViewController
    < // TODO: using rumpus stuff here for now. should change to chuck stuff
        RumpusServiceManager,
        RumpusUser,
        RumpusUserMetaData,
        AbstractUserService<RumpusUser, RumpusUserMetaData>, // TODO: Change to ChuckUserService when created
        RumpusAdminUserView
    > {

        private static final String NAME = "RumpusViewController";

        public ChuckViewController() {
            super(
                NAME,
                ChuckViewLoader.create());
        }
}
