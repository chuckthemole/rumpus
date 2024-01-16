package com.rumpus.rumpus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Session.CommonSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(ICommonController.PATH_PYCOMMON_API)
public class RumpusPycommonController extends RumpusRestController {

    @Autowired
    public RumpusPycommonController() {
            super();
    }

    /*
     * Testing to see if we can call pycommon from a rest api.
     */
    @GetMapping(value = "/test")
    public ResponseEntity<CommonSession> test(HttpServletRequest request) {
        LOG.info("RumpusPycommonController::test()");
        HttpSession session = request.getSession();
        final String uri = "http://localhost:8000/voice_assist/";
        RestTemplate restTemplate = new RestTemplate();
        final String result = restTemplate.getForObject(uri, String.class);
        session.setAttribute("pycommon", result);
        LogBuilder.logBuilderFromStringArgs("Pycommon call from rest api: ", result).info();
        return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(session), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/scraper_test")
    public ResponseEntity<CommonSession> scraperTest(HttpServletRequest request) {
        LOG.info("RumpusPycommonController::scraperTest()");
        HttpSession session = request.getSession();
        final String uri = "http://localhost:8000/scraper/chatgpt/";
        RestTemplate restTemplate = new RestTemplate();
        final String result = restTemplate.getForObject(uri, String.class);
        session.setAttribute("scraper test", result);
        LogBuilder.logBuilderFromStringArgs("Pycommon scraper test: ", result).info();
        return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(session), HttpStatus.ACCEPTED);
    }
}
