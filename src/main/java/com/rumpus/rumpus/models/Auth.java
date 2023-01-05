/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rumpus.rumpus.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Chuck
 */

public class Auth extends RumpusModel {
    private Level level;
    private Set<Privilege> privileges;

    public static Auth createAdminAuth(int id) {
        Auth a = new Auth();
        a.level = Level.ADMIN;
        a.privileges = new HashSet<Privilege>();
        a.privileges.add(Privilege.READ);
        a.privileges.add(Privilege.WRITE);
        a.privileges.add(Privilege.DELETE);
        return a;
    }
    public static Auth createUserAuth(int id) {
        Auth a = new Auth();
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
