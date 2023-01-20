package com.rumpus.rumpus.models;
import com.rumpus.common.Model;

import java.util.HashMap;
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
    public User(){
        super(MODEL_NAME);
        init();
    }
    public User(Map<String, String> initMap) {
        super(MODEL_NAME, initMap);
        init();
    }
    // public User(String user_name) {
    //     super(MODEL_NAME);
    //     this.userName = user_name;
    // }
    // public User(Long id, String user_name) {
    //     super(MODEL_NAME);
    //     this.id = id;
    //     this.userName = user_name;
    // }
    // public User(Long id, String user_name, int auth_id) {
    //     super(MODEL_NAME);
    //     this.id = id;
    //     this.userName = user_name;
    //     this.authId = auth_id;
    // }

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

    // // @Override // Override Model impl
    // public static Function<Map<String, String>, User> createFunction() {
    //     return (Map<String, String> initMap) -> {
    //         User user = new User();
    //         if(initMap.containsKey("name")) {
    //             user.setName(initMap.get("name"));
    //         }
    //         if(initMap.containsKey("id")) {
    //             user.setId(Long.parseLong(initMap.get("id")));
    //         }
    //         if(initMap.containsKey("auth_id")) {
    //             user.setAuth(Integer.parseInt(initMap.get("auth_id")));
    //         }
    //         return user;
    //     };
    // }

    // static factory methods
    public static User create(Map<String, String> initMap) {return new User(initMap);}
    public static User createWithName(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        return User.create(map);
    }

    // public static User createUser(String name) {
    //     User u = new User(name);
    //     return u;
    // }
    // public static User createUser(Long id, String name) {
    //     User u = new User(id, name);
    //     return u;
    // }
    // public static User createUser(Long id, String name, int auth_id) {
    //     User u = new User(id, name, auth_id);
    //     return u;
    // }
    
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
