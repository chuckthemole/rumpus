package com.rumpus.rumpus.controller;

import com.google.gson.Gson;
import com.rumpus.rumpus.models.*;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.ui.RumpusView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping("/api")
public class RumpusRestController {

    // @Autowired
    private RumpusView view;
    // @Autowired
    private IUserService rumpusUserService;

    @Autowired
    public RumpusRestController(IUserService service, RumpusView view) {
        this.rumpusUserService = service;
        this.view = view;
    }
    
    // @GetMapping("/")
    // public String index() {
    //     return "index";
    // }
    
    @GetMapping("/userid/{id}")
    public User getUserById(@PathVariable int id) {
        return rumpusUserService.get(id);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        Gson gson = new Gson();
        
        return new ResponseEntity<List<User>>(rumpusUserService.getAll(), HttpStatusCode.valueOf(200));
    }
    
    @GetMapping("/username/{name}")
    public User getUserByName(@PathVariable String name) {
        // return rumpusUserService.get(name);
        return null;
    }
}
