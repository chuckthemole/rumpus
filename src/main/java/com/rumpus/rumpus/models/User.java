package com.rumpus.rumpus.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public User(Map<String, String> attributeMap) {
        super(MODEL_NAME, attributeMap);
        init();
    }

    @Override
    public int init() {
        if(attributeMap == null || attributeMap.isEmpty()) {
            LOG.info("attributeMap is empty.");
            this.userName = NO_NAME;
            this.id = NO_ID;
            this.authId = EMPTY;
            return EMPTY;
        }
        if(attributeMap.containsKey("name")) {
            this.setName(attributeMap.get("name"));
        } else {
            this.setName(NO_NAME);
        }
        if(attributeMap.containsKey("id")) {
            this.setId(Long.parseLong(attributeMap.get("id")));
        } else {
            this.setId(NO_ID);
        }
        if(attributeMap.containsKey("auth_id")) {
            this.setAuth(Integer.parseInt(attributeMap.get("auth_id")));
        } else {
            this.setAuth(EMPTY);
        }
        statement();
        return SUCCESS;
    }

    // static factory methods
    public static User create(Map<String, String> attributeMap) {return new User(attributeMap);}
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
        attributeMap.put("name", n);
        this.userName = n;
    }
    public int getAuth() {
        return this.authId;
    }
    public void setAuth(int a) {
        attributeMap.put("auth_id", Integer.toString(a));
        this.authId = a;
    }

    @Override 
    public String toString() {
        return "Name: " + this.userName + " AuthId: " + this.authId + "\n";
    }

    private int statement() {
        setStatement(
            (PreparedStatement statement) -> {
                try {
                    statement.setString(1, userName);
                    statement.setLong(2, id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return  statement;
            }
        );
        return SUCCESS;
    }
}
