package com.rumpus.chuck;

import org.springframework.stereotype.Controller;

import com.rumpus.rumpus.controller.RumpusController;

@Controller
public class ChuckController extends RumpusController {

    private static final String NAME = "ChuckController";

    // Paths for charles pikaart thomas
    protected static final String PATH_CHARLES_PIKAART_THOMAS = "/charles_pikaart_thomas";
    protected static final String PATH_CHARLES_PIKAART_THOMAS_VIEW = "/charles_pikaart_thomas/view";

    // URIs
    protected static final String CHARLES_PIKAART_THOMAS_DEV_URI = "http://127.0.0.1:8000/";
    protected static final String CHARLES_PIKAART_THOMAS_BETA_URI = "http://127.0.0.1:8000/beta/";
    protected static final String CHARLES_PIKAART_THOMAS_LIVE_URI = "http:charles-pikaart-thomas.com/";

    public ChuckController() {
        super(NAME);
    }
    public ChuckController(String name) {
        super(name);
    }
}
