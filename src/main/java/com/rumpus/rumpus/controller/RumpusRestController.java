package com.rumpus.rumpus.controller;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Forum.ForumPost;
import com.rumpus.common.Forum.ForumPostNode;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Log.LogItem.LogItem;
import com.rumpus.common.Log.LogItem.LogItemCollection;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.rumpus.IRumpus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Generic rest controller for Rumpus
 * <p>
 * Only put generic rest controller methods here. If you need to add a rest controller method for a specific model, then create a new rest controller for that model.
 * <p>
 * TODO: maybe make this an abstract class and have a RumpusRestController and a RumpusUserRestController that extends this class with parametrized types
 * 
 * @author Chuck
 */
@RestController
@RequestMapping(ICommonController.PATH_API)
public class RumpusRestController extends AbstractRumpusController {

    @Autowired
    public RumpusRestController() {}

    @GetMapping(value = ICommonController.PATH_INDEX)
    public String getIndex() {
        return this.getClass().getSimpleName(); // TODO: look at this later
    }

    @GetMapping(value = "is_authenticated")
    public ResponseEntity<Boolean> getAuthenticationOfUser(Authentication authentication) {
        LOG("RumpusRestController::getAuthenticationOfUser()");
        boolean isAuthenticated = false;
        if(authentication != null) {
            isAuthenticated = authentication.isAuthenticated();
        } else {
            LOG("Authentication is null");
        }
        return new ResponseEntity<Boolean>(isAuthenticated, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = ICommonController.PATH_LOG_ACTION)
    public ResponseEntity<CommonSession> logAction(@RequestBody LogItem logItem, HttpServletRequest request) {
        LOG("RumpusRestController POST: /log_action");
        HttpSession session = request.getSession();
        LOG(logItem.toString());
        this.logManager.log(logItem);

        ResponseEntity<CommonSession> re = new ResponseEntity<>(new CommonSession(session), HttpStatus.CREATED);
        return re;
    }

    @GetMapping(value = "/logs/{page}")
    public ResponseEntity<LogItemCollection> getLogsForPage(@PathVariable("page") String page, HttpServletRequest request) {
        LOG("RumpusRestController GET: /logs");
        LogItemCollection logs = this.logManager.get(page);
        if(logs == null || logs.isEmpty()) {
            LOG("No logs found for page: " + page);
        }
        LOG("Successfully retrieved logs for page: " + page + "");
        return new ResponseEntity<>(logs, HttpStatus.CREATED);
    }

    @PostMapping(value = "/admin/forum_post")
    public ResponseEntity<CommonSession> adminForumPost(@RequestBody ForumPost forumPost, HttpServletRequest request) {
        HttpSession session = request.getSession();
        this.forumThreadManager.get(IRumpus.ADMIN_FORUM_THREAD_ID).addToSequence(ForumPostNode.createNodeFromForumPost(forumPost));
        LOG(forumPost.toString());
        return new ResponseEntity<>(new CommonSession(session), HttpStatus.CREATED);
    }

    @GetMapping(value = "/admin/forum_posts")
    public ResponseEntity<java.util.List<ForumPost>> getAdminForumThread(Authentication authentication) {
        LOG("RumpusRestController GET: /admin/forum_posts");
        if(authentication != null) {
            if(this.forumThreadManager != null) {
                final ForumThread forumThread = this.forumThreadManager.get(IRumpus.ADMIN_FORUM_THREAD_ID);
                if(forumThread != null) {
                    return new ResponseEntity<java.util.List<ForumPost>>(forumThread.toListOfTopLevel(), HttpStatus.ACCEPTED);
                }
            }
        }
        LOG("Error: unable to get admin forum posts");
        return null;
    }

    /** */
    @PostMapping(value = ICommonController.PATH_POST_USER_TIME_ZONE)
    public ResponseEntity<CommonSession> setMinuteDifferenceUTC(String minutesDifferenceUTC, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(CommonSession.UTC_TIME_DIFFERENCE, minutesDifferenceUTC);
        ResponseEntity<CommonSession> re = new ResponseEntity<>(new CommonSession(session), HttpStatus.CREATED);
        return re;
    }

    @GetMapping(value = "/python")
    public ResponseEntity<String> getPython() {
        LOG("RumpusRestController::getPython()");
        this.pythonInterpreter.execfile("src/main/python/voice_assistant.py");
        return new ResponseEntity<String>("test", HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/current_base_path")
    public ResponseEntity<String> currentBasePath() {
        LOG("RumpusRestController::getCurrentBasePath()");
        return new ResponseEntity<String>(this.getCurrentBasePath(), HttpStatus.ACCEPTED);
    }

    // @GetMapping(value = "/start_python")
    // public ResponseEntity<String> startPythonServer() {
    //     LOG("RumpusRestController::startPythonServer()");
    //     AbstractServer server = this.serverManager.get("PycommonServer");
    //     server.start();
    //     final String status = server.isRunning() ? "python server is running" : "python server is not running";
    //     return new ResponseEntity<String>(status, HttpStatus.ACCEPTED);
    // }

    // @GetMapping(value = "/stop_python")
    // public ResponseEntity<String> stopPythonServer() {
    //     LOG("RumpusRestController::stopPythonServer()");
    //     AbstractServer server = this.serverManager.get("PycommonServer");
    //     server.stop();
    //     final String status = server.isRunning() ? "python server is running" : "python server is not running";
    //     return new ResponseEntity<String>(status, HttpStatus.ACCEPTED);
    // }

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
    //     LOG("RumpusRestController::login()");
    //     LOG(userRequest.toString());
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
        LOG("Error logining in!!");
        return "Failure to login";
    }

    @PostMapping("/destroy")
	public void destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
	}

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
