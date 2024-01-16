package com.rumpus.rumpus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Controller.AbstractUserController;
import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@RestController
@RequestMapping(ICommonController.PATH_API)
public class RumpusUserRestController extends AbstractUserController
    <
        RumpusUser,
        RumpusUserMetaData,
        AbstractUserService<RumpusUser, RumpusUserMetaData>,
        RumpusAdminUserView
    > {

        private static final String NAME = "RumpusUserRestController";

        public RumpusUserRestController() {
            super(NAME);
        }
}
