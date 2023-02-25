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
import com.rumpus.common.Auth;
import com.rumpus.common.Authority;
import com.rumpus.common.GsonSerializer;

/**
 *
 * @author Chuck
 */
public class User extends RumpusModel<User> {

    // TODO  make use of some of the bool member variables, just setting to arbitrary values for now.
    private class Details implements UserDetails {

        private Auth authority;
        private String password;
        @Expose private String username;
        private boolean isAccountNonExpired;
        private boolean isAccountNonLocked;
        private boolean isCredentialsNonExpired;
        private boolean isEnabled;

        Details() {
            Authority auth = new Authority("USER");
            this.authority = new Auth("", Set.of(auth), "", "", new HashMap<>(), true);
            this.isAccountNonExpired = false;
            this.isAccountNonLocked = false;
            this.isCredentialsNonExpired = false;
            this.isEnabled = true;
        }
        Details(Auth authority, String password, String username, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
            this.authority = authority;
            this.password = password;
            this.username = username;
            this.isAccountNonExpired = isAccountNonExpired;
            this.isAccountNonLocked = isAccountNonLocked;
            this.isCredentialsNonExpired = isCredentialsNonExpired;
            this.isEnabled = isEnabled;
        }

        @Override
        public Set<Authority> getAuthorities() {
            return authority.getAuthorities();
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return this.isAccountNonExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
            return this.isAccountNonLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return this.isCredentialsNonExpired;
        }

        @Override
        public boolean isEnabled() {
            return this.isEnabled;
        }

        public void setAuthorities(Auth authority) {
            this.authority = authority;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setUserName(String username) {
            this.username = username;
        }

        public void setIsAccountNonExpired(boolean isAccountNonExpired) {
            this.isAccountNonExpired = isAccountNonExpired;
        }

        public void setIsAccountNonLocked(boolean isAccountNonLocked) {
            this.isAccountNonLocked = isAccountNonLocked;
        }

        public void setIsCredentialsNonExpired(boolean isCredentialsNonExpired) {
            this.isCredentialsNonExpired = isCredentialsNonExpired;
        }

        public void setIsEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
        }

    }

    Details userDetails;
    // @Expose private String userName;
    @Expose private String email;
    // private String password;
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
        if(this.attributes.containsKey("name")) {
            this.userDetails.setUserName(this.attributes.get("name"));
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
        if(this.attributes.containsKey("pass")) {
            this.setPassword(this.attributes.get("pass"));
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
        map.put("name", name);
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
        this.attributes.put("name", name);
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
        this.attributes.put("pass", password);
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
}
