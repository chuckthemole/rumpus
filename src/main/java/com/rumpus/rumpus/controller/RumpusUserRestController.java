package com.rumpus.rumpus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Controller.AbstractUserController;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.RumpusServiceManager;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@RestController
@RequestMapping(ICommonController.PATH_API)
public class RumpusUserRestController extends AbstractUserController
    <
        RumpusServiceManager,
        RumpusUser,
        RumpusUserMetaData,
        IRumpusUserService,
        RumpusAdminUserView
    > {

        private static final String NAME = "RumpusUserRestController";

        public RumpusUserRestController() {
            super(NAME);
        }
}
