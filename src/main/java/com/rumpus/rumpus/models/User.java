package com.rumpus.rumpus.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.annotations.Expose;
import com.rumpus.common.CommonAuthentication;
import com.rumpus.common.CommonAuthority;
import com.rumpus.common.CommonUserDetails;
import com.rumpus.common.GsonSerializer;

/**
 *
 * @author Chuck
 */
public class User extends RumpusModel<User> {

    Details userDetails;
    @Expose private String email;
    private int authId;
    private static final String MODEL_NAME = "userModel";
    
    // Ctors
    public User() {
        super(MODEL_NAME);
        super.statement = statement();
        init();
    }
    public User(Map<String, String> attributes) {
        super(MODEL_NAME, attributes);
        init();
    }

    @Override
    public int init() {
        this.userDetails = new Details();
        if(this.attributes == null || this.attributes.isEmpty()) {
            LOG.info("WARNING: AttributeMap is empty.");
            this.userDetails.setUserName(NO_NAME);
            this.id = NO_ID;
            this.authId = EMPTY;
            return EMPTY;
        }
        if(this.attributes.containsKey("username")) {
            this.userDetails.setUserName(this.attributes.get("username"));
        } else {
            this.userDetails.setUserName(NO_NAME);
        }
        if(this.attributes.containsKey("id")) {
            this.setId(this.attributes.get("id"));
        } else {
            this.setId(NO_ID);
        }
        if(this.attributes.containsKey("auth_id")) {
            this.setAuth(Integer.parseInt(this.attributes.get("auth_id")));
        } else {
            this.setAuth(EMPTY);
        }
        if(this.attributes.containsKey("email")) {
            this.setEmail(this.attributes.get("email"));
        } else {
            this.setEmail(NO_NAME);
        }
        if(this.attributes.containsKey("password")) {
            this.setPassword(this.attributes.get("password"));
        } else {
            this.setPassword(NO_NAME);
        }
        this.setStatement(statement());
        return SUCCESS;
    }

    // static factory methods
    public static User create(Map<String, String> attributes) {return new User(attributes);}
    public static User createWithName(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", name);
        return User.create(map);
    }
    
    // Getters Setters
    public Details getUserDetails() {
        return this.userDetails;
    }
    public void setUserDetails(Details userDetails) {
        this.userDetails = userDetails;
    }
    public String getUsername() {
        return this.userDetails.getUsername();
    }
    public void setUsername(String name) {
        this.attributes.put("username", name);
        this.userDetails.setUserName(name);
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
        this.attributes.put("email", email);
    }
    public String getPassword() {
        return this.userDetails.getPassword();
    }
    public void setPassword(String password) {
        this.attributes.put("password", password);
        this.userDetails.setPassword(password);
    }
    public int getAuth() {
        return this.authId;
    }
    public void setAuth(int a) {
        this.attributes.put("auth_id", Integer.toString(a));
        this.authId = a;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Name: ").append(this.name).append("\n")
            .append(" Email: ").append(this.email).append("\n")
            .append(" UserName: ").append(this.userDetails.getUsername()).append("\n")
            .append(" Password: ").append(this.userDetails.getPassword()).append("\n")
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
        LOG.info("User Name: " + user.getUsername());
        LOG.info("This User Name: " + this.getUsername());
        if(user.getUsername().equals(this.getUsername())) {
            LOG.info("User pass: " + user.getUserDetails().getPassword());
            LOG.info("This User pass: " + this.getUserDetails().getPassword());
            if(user.getUserDetails().getPassword().equals(this.getUserDetails().getPassword())) {
                return true;
            }
        }
        LOG.info("User email: " + user.getEmail());
        LOG.info("This User email: " + this.getEmail());
        if(user.email.equals(this.email)) {
            if(user.getUserDetails().getPassword().equals(this.getUserDetails().getPassword())) {
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
                    statement.setString(1, this.getUserDetails().getPassword());
                    statement.setString(2, this.getUserDetails().getUsername());
                    statement.setString(3, this.email);
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
        sb.append("  User name: ").append(this.getUsername());
        LOG.info(sb.toString());
        sb.setLength(0); // clear sb
        sb.append("  User email: ").append(this.email);
        LOG.info(sb.toString());
        sb.setLength(0);
        sb.append("  User password: ").append(this.getUserDetails().getPassword());
        LOG.info(sb.toString());
        return SUCCESS;
    }

    static private class UserGsonSerializer extends GsonSerializer<User> {

        @Override
        public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("username", user.getUsername());
            jsonObj.addProperty("email", user.email);
            jsonObj.addProperty("password", user.getUserDetails().getPassword());
            return jsonObj;
        }
    }
    static public UserGsonSerializer getSerializer() {
        return new UserGsonSerializer();
    }

    private class Details extends CommonUserDetails {
        Details() {super();}
    }
}
