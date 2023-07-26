package com.rumpus.rumpus.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.log.Log;
import com.rumpus.common.AbstractCommonController;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.LogItem;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.User.ActiveUserStore;
import com.rumpus.common.User.CommonAuthentication;
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.Footer;
import com.rumpus.rumpus.collections.RumpusUserCollection;
import com.rumpus.rumpus.models.*;
import com.rumpus.rumpus.views.RumpusViewLoader;

import jakarta.servlet.ServletException;
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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.SessionRepository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @GetMapping(value = PATH_INDEX)
    public String getIndex() {
        return NAME;
    }
    
    @GetMapping(value = PATH_GET_USERS + "/{sort}")
    public ResponseEntity<List<RumpusUser>> getAllUsers(@PathVariable("sort") String sort, HttpSession session) {

        // get users from service and store in collection for sorting
        RumpusUserCollection userCollection = new RumpusUserCollection(rumpusUserService.getAll());
        List<RumpusUser> users = null; // user list to return
        if(userCollection != null && !userCollection.isEmpty()) {
            // sort
            if(sort != null && !sort.isBlank()) {
                if(sort.equals("username")) {
                    users = userCollection.sortByUsername();
                } else if(sort.equals("email")) {
                    users = userCollection.sortByEmail();
                } else if(sort.equals("id")) {
                    users = userCollection.sortById();
                } else {
                    users = userCollection.sortByUsername();
                }
            } else { // if no sort then use default sort. 
                users = userCollection.sortByUsername();
            }
        } else {
            LOG.info("Error: Rumpus user service returned no users 1.");
        }
        if(users == null || users.isEmpty()) {
            LOG.info("Error: Rumpus user service returned no users 2.");
        }
        ResponseEntity<List<RumpusUser>> responseEntity = new ResponseEntity<>(users, HttpStatusCode.valueOf(200));
        LOG.info(responseEntity.getBody().toString());
        return responseEntity;
    }

    @PostMapping(value = RumpusController.PATH_USER)
    public ResponseEntity<CommonSession> userSubmit(@RequestBody RumpusUser newUser, HttpServletRequest request) {
        LOG.info("RumpusRestController POST: /user");
        // debugUser(newUser);
        HttpSession session = request.getSession();
        LOG.info("Creating user: " + newUser.toString());
        newUser.setMetaData(RumpusUserMetaData.createEmpty()); // new MetaData adds creation time
        RumpusUser user = this.rumpusUserService.add(newUser);

        // userManager.addUserToGroup(newUser.getUsername(), "USER");
        // userManager.createUser(newUser.getUserDetails());

        currentUserLogin(user, request);
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
            // .registerTypeAdapter(RumpusUser.class, RumpusUser.getSerializer())
            .registerTypeAdapter(RumpusUser.class, user.getTypeAdapter())
            .create();
        session.setAttribute("user", gson.toJson(user));

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        // @SuppressWarnings("unchecked")
		// List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		// if (messages == null) {
		// 	messages = new ArrayList<>();
		// 	request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		// }
		// messages.add(user.toString());
		// request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);

        ResponseEntity<CommonSession> re = new ResponseEntity<>(new CommonSession(session), HttpStatus.CREATED);
        return re;
    }

    @PostMapping(value = RumpusController.PATH_DELETE_USER)
    public ResponseEntity<CommonSession> deleteUser(@RequestBody String user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        this.rumpusUserService.remove(StringUtil.isQuoted(user) ? user.substring(1, user.length() - 1) : user);
        return new ResponseEntity<CommonSession>(new CommonSession(session), HttpStatus.CREATED);
    }

    @PostMapping(value = RumpusController.PATH_UPDATE_USER)
    public ResponseEntity<CommonSession> updateUser(@RequestBody RumpusUser user, HttpServletRequest request) {
        LOG.info("RumpusRestController POST: /api/update_user");
        HttpSession session = request.getSession();
        // this.rumpusUserService.remove(StringUtil.isQuoted(user) ? user.substring(1, user.length() - 1) : user);
        LogBuilder log = new LogBuilder("Update this user: ", user.toString());
        log.info();
        rumpusUserService.update(user.getId(), user);
        return new ResponseEntity<CommonSession>(new CommonSession(session), HttpStatus.CREATED);
    }

    // TODO this should be secured so user info is not visible
    @GetMapping(value = RumpusController.PATH_VALUE_GET_BY_USER_NAME)
    public ResponseEntity<RumpusUser> getUserByUsername(@PathVariable(PATH_VARIABLE_GET_BY_USER_NAME) String username, HttpServletRequest request) {
        return new ResponseEntity<RumpusUser>(this.rumpusUserService.get(username), HttpStatus.ACCEPTED);
    }

    // TODO this should be secured so user info is not visible
    @GetMapping(value = RumpusController.PATH_VALUE_GET_BY_USER_ID)
    public ResponseEntity<RumpusUser> getUserById(@PathVariable(PATH_VARIABLE_GET_BY_USER_ID) String id, HttpServletRequest request) {
        LOG.info("RumpusRestController::getUserById()");
        RumpusUser user = this.rumpusUserService.getById(id);
        if(user != null) {
            LogBuilder log = new LogBuilder("Retrieved user: ", user.toString());
            log.info();
            return new ResponseEntity<RumpusUser>(user, HttpStatus.ACCEPTED);
        }
        LogBuilder log = new LogBuilder("User with id '",  id, "' was not found.");
        log.error();
        return null;
    }

    @GetMapping(value = "current_user")
    public ResponseEntity<RumpusUser> getCurrentUser(Authentication authentication) {
        LOG.info("RumpusRestController::getCurrentUser()");
        if(authentication != null) {
            final String username = authentication.getName();
            final RumpusUser user = this.rumpusUserService.get(username);
            return new ResponseEntity<RumpusUser>(user, HttpStatus.ACCEPTED);
        }
        return null;
    }

    @GetMapping(value = "is_authenticated")
    public ResponseEntity<Boolean> getAuthenticationOfUser(Authentication authentication) {
        LOG.info("RumpusRestController::getAuthenticationOfUser()");
        boolean isAuthenticated = false;
        if(authentication != null) {
            isAuthenticated = authentication.isAuthenticated();
        }
        return new ResponseEntity<Boolean>(isAuthenticated, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = RumpusController.PATH_LOG_ACTION)
    public ResponseEntity<CommonSession> logAction(@RequestBody LogItem logItem, HttpServletRequest request) {
        LOG.info("RumpusRestController POST: /log_action");
        HttpSession session = request.getSession();
        LOG.info(logItem.toString());
        this.logManager.log(logItem);

        ResponseEntity<CommonSession> re = new ResponseEntity<>(new CommonSession(session), HttpStatus.CREATED);
        return re;
    }

    /** */
    @PostMapping(value = RumpusController.PATH_POST_USER_TIME_ZONE)
    public ResponseEntity<CommonSession> setMinuteDifferenceUTC(String minutesDifferenceUTC, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(CommonSession.UTC_TIME_DIFFERENCE, minutesDifferenceUTC);
        ResponseEntity<CommonSession> re = new ResponseEntity<>(new CommonSession(session), HttpStatus.CREATED);
        return re;
    }

    // public void login(HttpServletRequest req, String user, String pass) {
    //     UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user, pass);
    //     Authentication auth = this.authManager.authenticate(authReq);
        
    //     SecurityContext sc = SecurityContextHolder.getContext();
    //     sc.setAuthentication(auth);
    //     HttpSession session = req.getSession(true);
    //     session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
    // }

    // @RequestMapping(value = "/login", method = RequestMethod.POST)
    // public Authentication login(@RequestBody RumpusUser userRequest) {
    //     LOG.info("RumpusRestController::login()");
    //     LOG.info(userRequest.toString());
    //     Authentication authentication = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
    //     boolean isAuthenticated = isAuthenticated(authentication);
    //     if(isAuthenticated) {
    //         SecurityContextHolder.getContext().setAuthentication(authentication);
    //     }
    //     return authentication;
    // }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


    // ********************************************************************************
    // TODO check if I'm using these functions below here. Idk if I am - chuck 6/5/2023
    @GetMapping("/login_failure")
    public String loginFailure() {
        LOG.info("Error logining in!!");
        return "Failure to login";
    }

    @PostMapping("/destroy")
	public void destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
	}

    // get the current user's username
    @GetMapping("/username")
    public ResponseEntity<String> getUsername(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();   // gets detailed user info -> .getPrincipal().toString();
        ResponseEntity<String> re = new ResponseEntity<>(username, HttpStatus.CREATED);
        return re;
    }
}
