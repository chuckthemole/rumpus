package com.rumpus.chuck;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.chuck.views.ChuckViewLoader;
import com.rumpus.common.Controller.AbstractViewController;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping(ChuckController.PATH_CHARLES_PIKAART_THOMAS_VIEW)
@CrossOrigin(origins = {ChuckController.CHARLES_PIKAART_THOMAS_DEV_URI, ChuckController.CHARLES_PIKAART_THOMAS_BETA_URI, ChuckController.CHARLES_PIKAART_THOMAS_LIVE_URI})
public class ChuckViewController extends AbstractViewController {

    private static final String NAME = "RumpusViewController";

    public ChuckViewController() {
        super(NAME, ChuckViewLoader.create());
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
