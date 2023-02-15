package com.rumpus.rumpus.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
        super.statement = statement();
        init();
    }
    public User(Map<String, String> attributeMap) {
        super(MODEL_NAME, attributeMap);
        init();
    }

    @Override
    public int init() {
        if(attributeMap == null || attributeMap.isEmpty()) {
            LOG.info("WARNING: AttributeMap is empty.");
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
        this.setStatement(statement());
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
        sb.append("\n Name: ").append(this.name).append("\n")
            .append(" Email: ").append(this.email).append("\n")
            .append(" UserName: ").append(this.userName).append("\n")
            .append(" Password: ").append(this.password).append("\n")
            .append(" AuthId: ").append(this.authId).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;
        LOG.info("User Name: " + user.userName);
        LOG.info("This User Name: " + user.userName);
        if(user.userName.equals(this.userName)) {
            LOG.info("User pass: " + user.userName);
            LOG.info("This User pass: " + user.userName);
            if(user.password.equals(this.password)) {
                return true;
            }
        }
        LOG.info("User email: " + user.userName);
        LOG.info("This User email: " + user.userName);
        if(user.email.equals(this.email)) {
            LOG.info("User pass: " + user.userName);
            LOG.info("This User pass: " + user.userName);
            if(user.password.equals(this.password)) {
                return true;
            }
        }
        return false;
    }

    private Function<PreparedStatement, PreparedStatement> statement() {
        return(
            (PreparedStatement statement) -> {
                try {
                    // debugUser();
                    statement.setString(1, password);
                    statement.setString(2, userName);
                    statement.setString(3, email);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return  statement;
            }
        );
    }

    private int debugUser() {
        LOG.info("User statement()");
        StringBuilder sb = new StringBuilder();
        sb.append("  User name: ").append(this.userName);
        LOG.info(sb.toString());
        sb.setLength(0); // clear sb
        sb.append("  User email: ").append(this.email);
        LOG.info(sb.toString());
        sb.setLength(0);
        sb.append("  User password: ").append(this.password);
        LOG.info(sb.toString());
        return SUCCESS;
    }
}
