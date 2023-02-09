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
    private String email;
    private String password;
    private int authId;
    private static final String MODEL_NAME = "userModel";
    
    // Ctors
    public User() {
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
            this.setUserName(attributeMap.get("name"));
        } else {
            this.setUserName(NO_NAME);
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
        if(attributeMap.containsKey("email")) {
            this.setEmail(attributeMap.get("email"));
        } else {
            this.setEmail(NO_NAME);
        }
        if(attributeMap.containsKey("pass")) {
            this.setPassword(attributeMap.get("pass"));
        } else {
            this.setPassword(NO_NAME);
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
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String name) {
        attributeMap.put("name", name);
        this.userName = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
        attributeMap.put("email", email);
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
        attributeMap.put("pass", password);
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
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(this.name).append("\n")
            .append("Email: ").append(this.email).append("\n")
            .append("UserName: ").append(this.userName).append("\n")
            .append("AuthId: ").append(this.authId).append("\n");
        return sb.toString();
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
