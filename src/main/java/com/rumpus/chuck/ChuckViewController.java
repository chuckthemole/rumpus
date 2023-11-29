package com.rumpus.chuck;

import java.util.List;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.chuck.views.ChuckViewLoader;
import com.rumpus.common.Controller.AbstractViewController;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.Resource;

import jakarta.servlet.http.HttpServletRequest;

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
}
