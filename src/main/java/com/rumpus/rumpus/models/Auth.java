/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rumpus.rumpus.models;

import com.rumpus.common.Model;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Chuck
 */

public class Auth extends RumpusModel<Auth> {
    private Level level;
    private Set<Privilege> privileges;
    private static final String MODEL_NAME = "authModel";

    // Ctors
    public Auth() {super(MODEL_NAME);}
    public Auth(Long id) {super(MODEL_NAME, id);}

    @Override // Override Model impl
    public Supplier<Auth> createFunction() {
        return () -> {
            Auth auth = createNewAuth();
            String level = rawInitList.containsKey("level") ? rawInitList.get("level") : "";
            if(level.equals("admin")) {
                auth.setLevel(Level.ADMIN);
            } else if(level.equals("mod")) {
                auth.setLevel(Level.MODERATOR);
            } else if(level.equals("guest")) {
                auth.setLevel(Level.GUEST);
            } else if(level.equals("user")) {
                auth.setLevel(Level.USER);
            }
            return auth;
        };
    }

    // static factory methods
    public static Auth createNewAuth() {return new Auth();}
    public static Auth createAdminAuth(Long id) {
        Auth a = new Auth(id);
        a.level = Level.ADMIN;
        a.privileges = new HashSet<Privilege>();
        a.privileges.add(Privilege.READ);
        a.privileges.add(Privilege.WRITE);
        a.privileges.add(Privilege.DELETE);
        return a;
    }
    public static Auth createUserAuth(Long id) {
        Auth a = new Auth(id);
        a.level = Level.USER;
        a.privileges = new HashSet<Privilege>();
        a.privileges.add(Privilege.READ);
        return a;
    }
    
    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level l) {
        this.level = l;
    }

    @Override 
    public String toString() {
        return "User level: " + this.level + "\n";
    }

    public enum Level {
        ADMIN("admin"),
        MODERATOR("mod"),
        GUEST("guest"),
        USER("user");
    
        private final String level;
    
        private Level(String l) {
            this.level = l;
        }
    }
    public enum Privilege {
        READ("read"),
        WRITE("write"),
        DELETE("delete");

        private final String privilege;

        private Privilege(String p) {
            this.privilege = p;
        }
    }
}
