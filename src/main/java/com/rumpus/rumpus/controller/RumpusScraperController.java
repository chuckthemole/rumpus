package com.rumpus.rumpus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Session.CommonSession;
import com.rumpus.rumpus.scraper.ChatGPT;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(RumpusController.PATH_SCRAPER)
public class RumpusScraperController extends RumpusController {
    @GetMapping(value = "/start/{scraper}")
    public ResponseEntity<CommonSession> startScraper(@PathVariable("scraper") String scraperName, HttpServletRequest request) {
        LOG.info("RumpusScraperController::startScraper()");
        HttpSession session = request.getSession();
        // if(!this.serverManager.containsKey(scraperName)) {
        //     session.setAttribute("server status", "Server not found: " + scraperName);
        //     return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(session), HttpStatus.NOT_FOUND);
        // }
        // this.serverManager.startServer(scraperName);
        // session.setAttribute("server status", this.serverManager.getServerStatus(scraperName));

        ChatGPT chatGPT = ChatGPT.getInstance();
        chatGPT.run();
        session.setAttribute("chatGPT", chatGPT.getBody());

        return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(session), HttpStatus.ACCEPTED);
    }
}
