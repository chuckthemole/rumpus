/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rumpus.rumpus.models;

import java.util.Set;
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
    public Auth() {super(MODEL_NAME);init();}
    public Auth(Map<String, String> attributeMap) {super(MODEL_NAME, attributeMap);init();}

    @Override
    public int init() {
        if(attributeMap == null || attributeMap.isEmpty()) {
            LOG.info("attributeMap is empty.");
            this.setLevel(Level.NONE);
            return EMPTY;
        }
        if(attributeMap.containsKey("level")) {
            String level = attributeMap.get("level");
            if(level.equals("admin")) {
                this.setLevel(Level.ADMIN);
            } else if(level.equals("mod")) {
                this.setLevel(Level.MODERATOR);
            } else if(level.equals("guest")) {
                this.setLevel(Level.GUEST);
            } else if(level.equals("user")) {
                this.setLevel(Level.USER);
            }
        }
        return SUCCESS;
    }

    // static factory methods
    public static Auth create(Map<String, String> attributeMap) {return new Auth(attributeMap);}
    public static Auth createAdminAuth() {
        Auth a = new Auth();
        a.level = Level.ADMIN;
        a.privileges = new HashSet<Privilege>();
        a.privileges.add(Privilege.READ);
        a.privileges.add(Privilege.WRITE);
        a.privileges.add(Privilege.DELETE);
        return a;
    }
    public static Auth createUserAuth() {
        Auth a = new Auth();
        a.level = Level.USER;
        a.privileges = new HashSet<Privilege>();
        a.privileges.add(Privilege.READ);
        return a;
    }
    
    // Getters Setters
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

    // Auth Enums
    public enum Level {
        ADMIN("admin"),
        MODERATOR("mod"),
        GUEST("guest"),
        USER("user"),
        NONE("none");
    
        private final String level;
    
        private Level(String l) {
            this.level = l;
        }
    }
    public enum Privilege {
        READ("read"),
        WRITE("write"),
        DELETE("delete"),
        NONE("none");

        private final String privilege;

        private Privilege(String p) {
            this.privilege = p;
        }
    }
}
