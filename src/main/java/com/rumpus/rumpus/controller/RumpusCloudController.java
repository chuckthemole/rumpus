package com.rumpus.rumpus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.AbstractCloudController;
import com.rumpus.common.Controller.ICommonController;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@RestController
@RequestMapping(ICommonController.PATH_CLOUD)
public class RumpusCloudController extends AbstractCloudController
    <
        RumpusUser,
        RumpusUserMetaData,
        IRumpusUserService,
        RumpusAdminUserView
    > {

        private static final String NAME = "RumpusCloudController";

        public RumpusCloudController() {
            super(NAME);
        }
}
