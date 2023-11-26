package com.rumpus.chuck;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping(ChuckController.PATH_CHARLES_PIKAART_THOMAS_VIEW)
@CrossOrigin(origins = {ChuckController.CHARLES_PIKAART_THOMAS_DEV_URI, ChuckController.CHARLES_PIKAART_THOMAS_BETA_URI, ChuckController.CHARLES_PIKAART_THOMAS_LIVE_URI})
public class ChuckViewController extends ChuckController { //TODO - can we have something like AbstractViewController? Maybe an annotation? Something like @ViewController? I'd like the view methods to be overridden in the subclass.

    private static final String NAME = "RumpusViewController";

    public ChuckViewController() {super(NAME);}

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
