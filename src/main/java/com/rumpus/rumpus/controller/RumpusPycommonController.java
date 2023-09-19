package com.rumpus.rumpus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Session.CommonSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(RumpusController.PATH_PYCOMMON_API)
public class RumpusPycommonController extends RumpusRestController {

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

}
