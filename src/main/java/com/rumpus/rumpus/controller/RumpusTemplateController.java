/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rumpus.rumpus.controller;

import com.rumpus.common.ActiveUserStore;
import com.rumpus.rumpus.models.*;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.ui.RumpusView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Chuck
 */

@Controller
@RequestMapping(RumpusController.PATH_INDEX)
public class RumpusTemplateController extends RumpusController {

    @GetMapping(value = PATH_INDEX)
	public String index(Model model) {
        model.addAttribute(MODEL_USER, new User());
		return TEMPLATE_INDEX;
	}

    /**
     * 
     * 
     * @return redirect after user submit
     */
    @PostMapping(PATH_USER)
    public String userSubmit(@ModelAttribute User user, BindingResult bindingResult, Model model) {
        LOG.info("RumpusTemplateController::userSubmit()");
        // if (bindingResult.hasErrors()) {
        //     System.out.println("ERROR in user Form");
        //     for(ObjectError error : bindingResult.getAllErrors()) {
        //         System.out.println(error.toString());
        //     }
        //     return "redirect:/";
        // } else {
            model.addAttribute(MODEL_USER, user);
            return PATH_REDIRECT;
        // }
    }

    private void debugUser(User user) {
        System.out.println("  User name: " + user.getUsername());
        System.out.println("  User email: " + user.getEmail());
        System.out.println("  User password: " + user.getPassword());
    }

    // private RumpusView view;
    // private IUserService userService;

    // public RumpusController(IUserService userService, RumpusView view) {
    //     this.userService = userService;
    //     this.view = view;
    // }
    
    // public void run() {
    //     boolean keepGoing = true;
    //     while (keepGoing) {
    //         int menuSelection = getMenuSelection();
    //         switch(menuSelection) {
    //             case 1:
    //                 addUser();
    //                 break;
    //             case 2:
    //                 displayUsers();
    //                 break;
    //             case 3:
    //                 removeUser();
    //                 break;
    //             case 4:
    //                 keepGoing = false;
    //                 break;
    //             default:
    //                 unknownCommand();
    //                 break;
    //         }      
    //     }
    //     exitMessage();
    //     System.exit(0);
    // }
    
    // private int getMenuSelection() {
    //     return view.printMenuAndGetSelection();
    // }

    // @PostMapping("/add_user")
    // @ResponseStatus(HttpStatus.CREATED)
    // private void addUser() {
    //     view.displayAddUserBanner();
    //     User user = User.createWithName(view.readUserName());
    //     userService.add(user);
    // }

    // @PostMapping("/remove_user")
    // @ResponseStatus(HttpStatus.CREATED)
    // private void removeUser() {
    //     view.displayRemoveUserBanner();
    //     userService.remove(view.readUserId());
    // }

    // @GetMapping("/all_users")
    // private List<User> displayUsers() {
    //     List<User> list = new ArrayList();
    //     userService.getAll().forEach(user -> {
    //         list.add(user);
    //     });
    //     // Collections.sort(list);
    //     view.displayUsers(list);
    //     return list;
    // }

    
    // @PostMapping("/begin")
    // @ResponseStatus(HttpStatus.CREATED)
    // private Integer createRandomFourDigitNumber() {
    //     Integer gameID = userService.createRandomFourDigitNumber();
    //     if (gameID == null) {
    //         view.displayNumberCreatedUnsuccessfulBanner();
    //     } else {
    //         view.displayNumberCreatedSuccessfulBanner();
    //         view.print("ID=" + gameID);
    //         view.displayNumberCreated(userService.retrieveHiddenNumber(gameID));
    //     }
        
    //     return gameID;
    // }
    
    // // @RequestBody Round round sending as JSON to hid url
    // @PostMapping("/guess")
    // private ResponseEntity<Round> guess(int guess, int gameID) {
    //     Round round = userService.guessNumber(guess, gameID);
        
    //     if (round == null) {
    //         return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    //     }
    //     return ResponseEntity.ok(round);
        
    // }
    
    // @GetMapping("/getallnumbers")
    // private List<Integer> getAndDisplayNumbers() {
    //     List<Integer> list = new ArrayList();
    //     userService.getNumbers().forEach(number -> {
    //         list.add(number.getNumber());
    //     });
    //     Collections.sort(list);
    //     view.displayNumbers(list);
        
    //     return list;
    // }
    
    // @GetMapping("/game")
    // private ResponseEntity<List<Game>> getGames() {
    //     List<Game> games = new ArrayList<>();
    //     games.addAll(userService.getGames());
        
    //     if (games.size() < 1) {
    //         return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    //     }
    //     return ResponseEntity.ok(games);
    // }
    
    // @GetMapping("/game/{id}")
    // private ResponseEntity<Game> findByID(@PathVariable int id) {
    //     Game game = userService.getGame(id);
    //     if (game == null) {
    //         return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    //     }
    //     return ResponseEntity.ok(game);
    // }
    
    // private void guessNumber() {
    //     view.displayGuessNumberBanner();
    //     List<Integer> list = new ArrayList();
        
    //     userService.getNumbers().forEach(number -> {
    //         list.add(number.getNumber());
    //     });
    //     if (list.size() > 0) {
    //         Collections.sort(list);
    //         int choice = view.getGame(list);
    //         playGame(choice);
    //     } else {
    //         view.displayGuessNumberNoNumbersCreatedBanner();
    //     }
    // }
    
    // @GetMapping("/rounds/{id}")
    // private ResponseEntity<List<Round>> findRoundsByGameID(@PathVariable int id) {
    //     List<Round> rounds = userService.getRounds(id);

    //     if (rounds.size() < 1) {
    //         return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    //     }
    //     return ResponseEntity.ok(rounds);
    // }
    
    // private void playGame(int number) {
    //     int guess = view.getGuessForGame();
    // }
    
    // private void unknownCommand() {
    //     view.displayUnknownCommandBanner();
    // }
    
    // private void exitMessage() {
    //     view.displayExitBanner();
    // }
    
    
}
