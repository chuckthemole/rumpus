package com.rumpus.rumpus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Controller.User.AbstractAdminUserRestController;
import com.rumpus.common.User.ICommonAuthentication;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.views.RumpusAdminUserView;

@RestController
@RequestMapping(ICommonController.PATH_API)
public class RumpusAdminUserRestController
        extends
            AbstractAdminUserRestController<RumpusUser, RumpusUserMetaData, IRumpusUserService, RumpusAdminUserView> {

    @Autowired
    public RumpusAdminUserRestController(
            IRumpusUserService userService,
            RumpusAdminUserView userView,
            ICommonAuthentication authentication) {
        super(
                ICommonController.PATH_API,
                userService,
                userView,
                authentication);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
