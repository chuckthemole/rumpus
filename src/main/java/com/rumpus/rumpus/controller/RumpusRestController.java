package com.rumpus.rumpus.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rumpus.common.ActiveUserStore;
import com.rumpus.common.CommonController;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.rumpus.models.*;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.ui.RumpusView;
import com.rumpus.rumpus.views.Footer;
import com.rumpus.rumpus.views.IViewLoader;
import com.rumpus.rumpus.views.ViewLoader;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.session.SessionRepository;
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
import org.springframework.web.server.WebSession;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.session.WebSessionManager;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping(RumpusController.PATH_API)
public class RumpusRestController extends RumpusController {

    private static final String NAME = "RumpusRestController";

    public RumpusRestController() {super(NAME);}

    // @Autowired
    // public RumpusRestController(IUserService service, IViewLoader viewLoader, RumpusView view, JdbcUserDetailsManager userManager, ActiveUserStore activeUserStore) {
    //     super(NAME, service, viewLoader, view, userManager, activeUserStore);
    // }
    
    // @GetMapping("/websessions")
    // public Mono<String> getSession(SessionRepository<CommonSession> sessions) {
    //     sessions.

    //     session.getAttributes().putIfAbsent("note", "Howdy Cosmic Spheroid!");
    //     return Mono.just((String) session.getAttributes().get("note"));
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
    public ResponseEntity<List<User>> getAllUsers(HttpSession session) {
        // Gson gson = new Gson();
        List<User> users = rumpusUserService.getAll();
        // for(User user : users) {
        //     debugUser(user);
        // }
        
        return new ResponseEntity<List<User>>(users, HttpStatusCode.valueOf(200));
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
    public ResponseEntity<CommonSession> userSubmit(@RequestBody User newUser, HttpServletRequest request) {
        LOG.info("RumpusRestController POST: /user");
        // debugUser(newUser);
        HttpSession session = request.getSession();
        User user = rumpusUserService.add(newUser);
        if (user == null) {
            LOG.info("ERROR: User is null.");
            session.setAttribute("status", "error creating user");
            return ResponseEntity.badRequest().body(new CommonSession(session));
        }
        session.setAttribute("loggedIn", true);

        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            // .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .disableHtmlEscaping()
            .registerTypeAdapter(User.class, User.getSerializer())
            .create();
        session.setAttribute("user", gson.toJson(user));

        // @SuppressWarnings("unchecked")
		// List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		// if (messages == null) {
		// 	messages = new ArrayList<>();
		// 	request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		// }
		// messages.add(user.toString());
		// request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);

        return new ResponseEntity<>(new CommonSession(session), HttpStatus.CREATED);
    }

    // can prolly do GET not POST
    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@RequestBody User user) {
        LOG.info("RumpusRestController POST: /login");
        // debugUser(user);
        if(rumpusUserService.login(user) == SUCCESS) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return null;
    }

    @PostMapping("/destroy")
	public void destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
	}

    private int debugUser(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n* * User * * \n");
        sb.append("  User name: ").append(user.getUsername()).append("\n");
        sb.append("  User email: ").append(user.getEmail()).append("\n");
        sb.append("  User password: ").append(user.getPassword()).append("\n");
        LOG.info(sb.toString());
        return SUCCESS;
    }
}
