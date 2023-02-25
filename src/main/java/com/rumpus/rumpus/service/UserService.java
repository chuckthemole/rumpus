package com.rumpus.rumpus.service;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.rumpus.data.IUserDao;
import com.rumpus.rumpus.models.User;

public class UserService extends RumpusService<User> implements IUserService {

    protected static final String NAME = "userService";
    private Map<String, User> users;

    public UserService(IUserDao userDao) {
        super(NAME, userDao);

        // keeping for faster lookup. May need to update if users are updated.
        users = new HashMap<>();
        dao.getAll().stream().forEach(u -> {
            users.put(u.getEmail(), u);
            users.put(u.getUsername(), u);
        });
    }

    @Override
    public int login(User user) {
        LOG.info("UserService::login()");

        // check for username or user email
        User foundUser = null;
        if(users.containsKey(user.getUsername())) {
            LOG.info("Found user name...");
            foundUser = users.get(user.getUsername());
        } else if(users.containsKey(user.getEmail())) {
            LOG.info("Found user email...");
            foundUser = users.get(user.getEmail());
        } else {
            LOG.info("User credentials are not correct.");
            return ERROR;
        }
        if(foundUser != null && foundUser.getPassword().equals(user.getPassword())) { // check password
            LOG.info("User credentials are correct. Logging in....");
            // TODO login
        }
        return SUCCESS;
    }

    // @Override
    // public User get(String name) {
    //     return IUserDao.class.cast(super.dao).get(name);
    // }
}
