package com.rumpus.buildshift.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Controller.AbstractViewController;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;
import com.rumpus.buildshift.service.IUserService;
import com.rumpus.buildshift.service.ServiceManager;
import com.rumpus.buildshift.views.ViewLoader;
import com.rumpus.buildshift.views.AdminUserView;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping(ICommonController.PATH_VIEW + "_bs")
public class BuildShiftViewController extends
        AbstractViewController<ServiceManager, User, UserMetaData, IUserService, AdminUserView> {

    @Autowired
    public BuildShiftViewController(ViewLoader viewLoader) {
        super(viewLoader);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}