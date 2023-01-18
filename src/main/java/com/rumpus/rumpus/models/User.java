package com.rumpus.rumpus.models;
import com.rumpus.common.Model;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author Chuck
 */
public class User extends RumpusModel<User> {
    private String userName;
    private int authId;
    private static final String MODEL_NAME = "userModel";
    
    // Ctors
    public User(){super(MODEL_NAME);}
    public User(String user_name) {
        super(MODEL_NAME);
        this.userName = user_name;
    }
    public User(int id, String user_name) {
        super(MODEL_NAME);
        this.id = id;
        this.userName = user_name;
    }
    public User(int id, String user_name, int auth_id) {
        super(MODEL_NAME);
        this.id = id;
        this.userName = user_name;
        this.authId = auth_id;
    }

    @Override // Override Model impl
    public Supplier<User> createFunction() {
        return () -> {
            User user = createNewUser();
            if(!rawInitList.get("name").isEmpty()) {
                user.setName(rawInitList.get("name"));
            }
            if(!rawInitList.get("id").isEmpty()) {
                user.setId(Integer.parseInt(rawInitList.get("id")));
            }
            if(!rawInitList.get("auth_id").isEmpty()) {
                user.setAuth(Integer.parseInt(rawInitList.get("auth_id")));
            }
            return user;
        };
    }

    // static factory methods
    public static User createNewUser() {return new User();}
    public static User createUser(String name) {
        User u = new User(name);
        return u;
    }
    public static User createUser(int id, String name) {
        User u = new User(id, name);
        return u;
    }
    public static User createUser(int id, String name, int auth_id) {
        User u = new User(id, name, auth_id);
        return u;
    }
    
    public String getName() {
        return this.userName;
    }
    public void setName(String n) {
        this.userName = n;
    }
    public int getAuth() {
        return this.authId;
    }
    public void setAuth(int a) {
        this.authId = a;
    }
    @Override 
    public String toString() {
        return "Name: " + this.userName + " AuthId: " + this.authId + "\n";
    }
}
