package com.rumpus.rumpus.controller;

import com.google.gson.Gson;
import com.rumpus.common.CommonController;
import com.rumpus.rumpus.models.*;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.ui.RumpusView;
import com.rumpus.rumpus.views.Footer;
import com.rumpus.rumpus.views.IViewLoader;
import com.rumpus.rumpus.views.ViewLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping("/api")
public class RumpusRestController extends CommonController {

    private static final String NAME = "RumpusRestController";
    // @Autowired
    private RumpusView view;
    // @Autowired
    private IUserService rumpusUserService;
    private IViewLoader viewLoader;

    @Autowired
    public RumpusRestController(IUserService service, IViewLoader viewLoader, RumpusView view) {
        super(NAME);
        this.rumpusUserService = service;
        this.viewLoader = viewLoader;
        this.view = view;
    }
    
    // @GetMapping("/")
    // public String index() {
    //     return "index";
    // }

    @GetMapping("/")
    public String getIndex() {
        return "hello";
    }
    
    @GetMapping("/userid/{id}")
    public User getUserById(@PathVariable int id) {
        return rumpusUserService.get(id);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        Gson gson = new Gson();
        
        return new ResponseEntity<List<User>>(rumpusUserService.getAll(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/footer")
    public ResponseEntity<Footer> getFooter() {
        return new ResponseEntity<Footer>(viewLoader.getFooter(), HttpStatusCode.valueOf(200));
    }
    
    @GetMapping("/username/{name}")
    public User getUserByName(@PathVariable String name) {
        // return rumpusUserService.get(name);
        return null;
    }

    // @PostMapping("/signup")
    // public int userSignup() {
    //     User user = new User(null);
    //     rumpusUserService.add(null);
    //     return SUCCESS;
    // }

    // @PostMapping("/signup")
    // public ResponseEntity<?> userSignupViaAjax(@RequestBody User user, Errors errors) {
    //     String userString = user.toString();
    //     if(userString.isEmpty()) {
    //         System.out.println("Error: User is empty!");
    //     } else {
    //         System.out.println("Printing user:");
    //         System.out.println(user.toString());
    //     }

    //     AjaxResponseBody<User> result = new AjaxResponseBody<>();

    //     //If error, just return a 400 bad request, along with the error message
    //     if (errors.hasErrors()) {

    //         result.setMsg(errors.getAllErrors()
    //                 .stream().map(x -> x.getDefaultMessage())
    //                 .collect(Collectors.joining(",")));
    //         return ResponseEntity.badRequest().body(result);

    //     }
    //     User newUser = rumpusUserService.add(user);
    //     if(newUser != null) {
    //         result.setMsg("Error creating user!");
    //     } else {
    //         result.setMsg("Success");
    //     }
    //     List<User> users = new ArrayList<>();
    //     users.add(newUser);
    //     result.setResult(users);

    //     return ResponseEntity.ok(result);

    // }

    @PostMapping("/signup")
    public ResponseEntity<?> userSignupViaAjax(@ModelAttribute User user, Errors errors) {
        String userString = user.toString();
        if(userString.isEmpty()) {
            System.out.println("Error: User is empty!");
        } else {
            System.out.println("Printing user:");
            System.out.println(user.toString());
        }

        AjaxResponseBody<User> result = new AjaxResponseBody<>();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);

        }
        User newUser = rumpusUserService.add(user);
        if(newUser != null) {
            result.setMsg("Error creating user!");
        } else {
            result.setMsg("Success");
        }
        List<User> users = new ArrayList<>();
        users.add(newUser);
        result.setResult(users);

        return ResponseEntity.ok(result);

    }

    // @GetMapping("/user")
    // public String userForm(Model model) {
    //     model.addAttribute("user", new User());
    //     return "user";
    // }

    @PostMapping("/user")
    public ResponseEntity<User> userSubmit(@RequestBody User newUser) {
        LOG.info("RumpusRestController POST: /user");
        StringBuilder sb = new StringBuilder();
        sb.append("  User name: ").append(newUser.getUserName());
        LOG.info(sb.toString());
        sb.setLength(0); // clear sb
        sb.append("  User email: ").append(newUser.getEmail());
        LOG.info(sb.toString());
        sb.setLength(0);
        sb.append("  User password: ").append(newUser.getPassword());
        LOG.info(sb.toString());
        User user = rumpusUserService.add(newUser);
        if (user == null) {
            LOG.info("User is null!");
            return null;
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
}
