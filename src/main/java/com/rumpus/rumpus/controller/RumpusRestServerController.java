package com.rumpus.rumpus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Server.AbstractServer;
import com.rumpus.common.Session.CommonSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(ICommonController.PATH_SERVER_API)
public class RumpusRestServerController extends RumpusRestController {

    @Autowired
    public RumpusRestServerController() {
            super();
    }

    @GetMapping(value = "/start/{server}")
    public ResponseEntity<CommonSession> startServer(@PathVariable("server") String serverName, HttpServletRequest request) {
        LOG.info("RumpusRestServerController::startServer()");
        HttpSession session = request.getSession();
        if(!this.serverManager.containsKey(serverName)) {
            session.setAttribute("server status", "Server not found: " + serverName);
            return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(session), HttpStatus.NOT_FOUND);
        }
        this.serverManager.startServer(serverName);
        session.setAttribute("server status", this.serverManager.getServerStatus(serverName));
        return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(session), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/stop/{server}")
    public ResponseEntity<CommonSession> stopServer(@PathVariable("server") String serverName, HttpServletRequest request) {
        LOG.info("RumpusRestServerController::stopServer()");
        HttpSession session = request.getSession();
        if(!this.serverManager.containsKey(serverName)) {
            session.setAttribute("server status", "Server not found: " + serverName);
            return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(session), HttpStatus.NOT_FOUND);
        }
        this.serverManager.stopServer(serverName);
        LOG.info("Server status in controller: " + this.serverManager.getServerStatus(serverName));
        session.setAttribute("server status", this.serverManager.getServerStatus(serverName));
        return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(session), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/get_servers")
    public java.util.List<AbstractServer> getServers(HttpServletRequest request) {
        LOG.info("RumpusRestServerController::getServers()");
        return this.serverManager.getAll();
    }

    // private String getServerStatus(AbstractServer server, String serverName) {
    //     return serverName + (server.getIsRunning() ? ": server is running" : ": server is not running");
    // }


}