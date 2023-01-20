package com.rumpus.rumpus.models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chuck
 */
public class User extends RumpusModel<User> {
    private String userName;
    private int authId;
    private static final String MODEL_NAME = "userModel";
    
    // Ctors
    public User(){
        super(MODEL_NAME);
        init();
    }
    public User(Map<String, String> initMap) {
        super(MODEL_NAME, initMap);
        init();
    }

    @Override
    public int init() {
        if(initMap == null || initMap.isEmpty()) {
            LOG.info("initMap is empty.");
            this.userName = NO_NAME;
            this.id = NO_ID;
            this.authId = EMPTY;
            return EMPTY;
        }
        if(initMap.containsKey("name")) {
            this.setName(initMap.get("name"));
        } else {
            this.setName(NO_NAME);
        }
        if(initMap.containsKey("id")) {
            this.setId(Long.parseLong(initMap.get("id")));
        } else {
            this.setId(NO_ID);
        }
        if(initMap.containsKey("auth_id")) {
            this.setAuth(Integer.parseInt(initMap.get("auth_id")));
        } else {
            this.setAuth(EMPTY);
        }
        return SUCCESS;
    }

    // static factory methods
    public static User create(Map<String, String> initMap) {return new User(initMap);}
    public static User createWithName(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        return User.create(map);
    }
    
    // Getters Setters
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
