package com.rumpus.rumpus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.AbstractCloudController;
import com.rumpus.common.Controller.ICommonController;

@RestController
@RequestMapping(ICommonController.PATH_CLOUD)
public class RumpusCloudController extends AbstractCloudController {

    public RumpusCloudController() {
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
