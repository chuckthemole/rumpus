package com.rumpus.rumpus.controller;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.AbstractViewController;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.Resource;
import com.rumpus.rumpus.views.RumpusViewLoader;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping(RumpusController.PATH_VIEW)
public class RumpusViewController extends AbstractViewController {

    private static final String NAME = "RumpusViewController";

    public RumpusViewController() {
        super(NAME, RumpusViewLoader.create());
    }
}
