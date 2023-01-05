/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rumpus.rumpus.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Chuck
 */
public class User extends RumpusModel {
    private String userName;
    private int authId;
    
    // Ctors
    public User(){}
    public User(String user_name) {
        this.userName = user_name;
    }
    public User(int id, String user_name) {
        this.id = id;
        this.userName = user_name;
    }
    public User(int id, String user_name, int auth_id) {
        this.id = id;
        this.userName = user_name;
        this.authId = auth_id;
    }

    // static factory methods
    public static User createUser(String name) {
        User u = new User(name);
        return u;
    }
    public static User createUser(int id, String name) {
        User u = new User(id, name);
        return u;
    }
    public static User createUser(int id, String name, int auth_id) {
        User u = new User(id, name, auth_id);
        return u;
    }
    
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
