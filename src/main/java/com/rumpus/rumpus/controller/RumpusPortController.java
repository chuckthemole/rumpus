package com.rumpus.rumpus.controller;

import com.rumpus.common.Controller.ICommonController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Not really using this right now. Had an idea to use the port manager but am not using it right now. I will keep it here for now. -chuck 2024-2-13
 */
@RestController
@RequestMapping(ICommonController.PATH_PORTS_API)
public class RumpusPortController extends AbstractRumpusController {

    private static final String NAME = "RumpusPortController";
    private static final String PORT = "8089"; // TODO: USE ENVIRONMENT

    private static com.rumpus.common.Server.Port.PortManager rumpusPortManager; // TODO: should use service dao instead of direct access to the port manager

    static {
        RumpusPortController.rumpusPortManager = com.rumpus.common.Server.Port.PortManager.getPortManager();
        com.rumpus.common.Server.Port.PortManager.setAdminPort(RumpusPortController.PORT);
    }

    public RumpusPortController() {
        super(NAME);
    }

    @GetMapping(value = "get_ports")
    public ResponseEntity<java.util.Set<String>> getPorts(Authentication authentication) {
        LOG("RumpusPortController::getPorts()");
        return new ResponseEntity<java.util.Set<String>>(rumpusPortManager, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "add_port/{port}")
    public ResponseEntity<Boolean> addPort(@PathVariable("port") String port) {
        LOG("RumpusPortController::addPort()");
        return new ResponseEntity<Boolean>(rumpusPortManager.add(port), HttpStatus.ACCEPTED);
    }
}
