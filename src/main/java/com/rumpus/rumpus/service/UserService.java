package com.rumpus.rumpus.service;

import com.rumpus.rumpus.data.IUserDao;
import com.rumpus.rumpus.models.User;

public class UserService extends RumpusService<User> implements IUserService {

    protected static final String NAME = "userService";

    public UserService(IUserDao userDao) {
        super(NAME, userDao);
    }

    // @Override
    // public User get(String name) {
    //     return IUserDao.class.cast(super.dao).get(name);
    // }
}
