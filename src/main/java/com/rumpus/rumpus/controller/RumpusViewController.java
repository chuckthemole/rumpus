package com.rumpus.rumpus.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping(RumpusController.PATH_VIEW)
public class RumpusViewController extends RumpusController {

    private static final String NAME = "RumpusViewController";

    public RumpusViewController() {super(NAME);}

    @GetMapping(PATH_FOOTER)
    public ResponseEntity<Footer> getFooter() {
        return new ResponseEntity<Footer>(viewLoader.getFooter(), HttpStatusCode.valueOf(200));
    }

    @GetMapping(PATH_USER_TABLE)
    public ResponseEntity<String> getUserTable() {
        return new ResponseEntity<String>(viewLoader.getUserTable().getTable(), HttpStatusCode.valueOf(200));
    }

    @GetMapping(PATH_HEADER)
    public ResponseEntity<Header> getHeader() {
        return new ResponseEntity<Header>(viewLoader.getHeader(), HttpStatusCode.valueOf(200));
    }
}
