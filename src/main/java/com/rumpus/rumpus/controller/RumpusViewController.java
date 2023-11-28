package com.rumpus.rumpus.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.AbstractViewController;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.rumpus.views.RumpusViewLoader;

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

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ResponseEntity<Footer> getFooter() {
        return new ResponseEntity<Footer>(viewLoader.getFooter(), HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<Header> getHeader() {
        return new ResponseEntity<Header>(viewLoader.getHeader(), HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<String> getUserTable() {
        return new ResponseEntity<String>(viewLoader.getUserTable().getTable(), HttpStatusCode.valueOf(200));
    }
}
