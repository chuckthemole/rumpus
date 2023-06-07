package com.rumpus.rumpus.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.rumpus.models.User;

public class UserDetailsService implements IUserService {

    private JdbcUserDetailsManager manager;

    public UserDetailsService(JdbcUserDetailsManager manager) {
        this.manager = manager;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public User get(String name) {
        UserDetails details = this.manager.loadUserByUsername(name);
        return null;
    }

    @Override
    public List<User> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public User add(User rumpusModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public boolean remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public String name() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'name'");
    }

    @Override
    public int login(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public boolean remove(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public User update(String oldModelKey, User updatedModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public User update(String oldModelKey, User updatedModel, String condition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
