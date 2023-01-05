package com.rumpus.rumpus.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import com.rumpus.rumpus.models.*;

/**
 *
 * @author Chuck
 */

public class RumpusView {
    private IRumpusIO io;
    
    public RumpusView(IRumpusIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* Rupmus");
        io.print("* 1. Create User");
        io.print("* 2. Display All User");
        io.print("* 3. Delete User");
        io.print("* 4. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print(""); 

        return io.readInt("Please select from the above choices.", 1, 4);
    }

    public void displayAddUserBanner() {
        io.print("* * * * * Add User * * * * * ");
    }
    public void displayRemoveUserBanner() {
        io.print("* * * * * Remove User * * * * * ");
    }
    public String readUserName() {
        return io.readString("Enter user name: ");
    }
    public int readUserId() {
        return io.readInt("Enter user id: ");
    }
    public void displayUsers(List<User> users) {
        users.forEach(user -> {
            io.print(user.getName());
        });
    }
    
    public void displayNumberCreatedUnsuccessfulBanner() {
        io.print("* * * * * * Number Creation unsuccessful * * * * * *");
    }
    
    public void displayNumberCreatedSuccessfulBanner() {
        io.print("* * * * * * Number Creation Successful * * * * * *");
    }
    
    public void displayNumberCreated(Integer number) {
        io.print("Number created: " + number);
    }
    
    public void print(String s) {
        io.print(s);
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
    
    public void displayNumbers(List<Integer> list) {
        io.print("* * * * * * Numbers Created * * * * * *");
        for (int i = 0; i < list.size(); i++) {
            io.print((i + 1) + ". " + list.get(i).toString());
        }
    }
    
    public void displayGuessNumberBanner() {
        io.print("* * * * * * Guess Number * * * * * *"); 
    }
    
    public void displayGuessNumberNoNumbersCreatedBanner() {
        io.print("* * * * * * No Numbers Created * * * * * *"); 
    }
    
    public Integer getGame(List<Integer> list) {
        Map<Integer, Integer> map = new HashMap();
        
        io.print("    -----Choose An Active Game-----");
        for (int i = 0; i < list.size(); i++) {
            map.put(i + 1, list.get(i));
            io.print((i + 1) + ". " + list.get(i).toString());
        }
        
        int selection = io.readInt("Please select a game for the above choices.", 1, list.size());
        return map.get(selection);
    }
    
    public Integer getGuessForGame() {
        io.print("* * * * * * Enter A Four Digit Number For Your Guess * * * * * *"); 
        return io.readInt("Please select a four digit number.", 1000, 9999);
    }
}

