package com.rumpus.rumpus.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.views.Footer;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping(RumpusController.PATH_CHARLES_PIKAART_THOMAS_VIEW)
@CrossOrigin(origins = {RumpusController.CHARLES_PIKAART_THOMAS_DEV_URI, RumpusController.CHARLES_PIKAART_THOMAS_BETA_URI, RumpusController.CHARLES_PIKAART_THOMAS_LIVE_URI})
public class CharlesPikaartThomasViewController extends RumpusController {

    private static final String NAME = "RumpusViewController";

    public CharlesPikaartThomasViewController() {super(NAME);}

    @GetMapping(PATH_FOOTER)
    public ResponseEntity<Footer> getFooter() {
        return new ResponseEntity<Footer>(viewLoader.getFooter(), HttpStatusCode.valueOf(200));
    }

    @GetMapping(PATH_USER_TABLE)
    public ResponseEntity<String> getUserTable() {
        return new ResponseEntity<String>(viewLoader.getUserTable().getTable(), HttpStatusCode.valueOf(200));
    }
}
