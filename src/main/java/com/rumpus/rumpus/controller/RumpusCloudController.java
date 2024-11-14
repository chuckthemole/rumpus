package com.rumpus.rumpus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.AbstractCloudController;
import com.rumpus.common.Controller.ICommonController;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.RumpusServiceManager;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@RestController
@RequestMapping(ICommonController.PATH_CLOUD)
public class RumpusCloudController extends AbstractCloudController
    <
        RumpusServiceManager,
        RumpusUser,
        RumpusUserMetaData,
        IRumpusUserService,
        RumpusAdminUserView
    > {

        public RumpusCloudController() {}

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toString'");
        }
}
