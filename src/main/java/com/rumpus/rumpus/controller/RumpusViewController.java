package com.rumpus.rumpus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Controller.AbstractViewController;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.views.RumpusAdminUserView;
import com.rumpus.rumpus.views.RumpusViewLoader;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping(ICommonController.PATH_VIEW)
public class RumpusViewController extends AbstractViewController
    <
        RumpusUser,
        RumpusUserMetaData,
        IRumpusUserService,
        RumpusAdminUserView
    > {

        private static final String NAME = "RumpusViewController";

        @Autowired
        public RumpusViewController() {
            super(
                NAME,
                RumpusViewLoader.create());
        }
}
