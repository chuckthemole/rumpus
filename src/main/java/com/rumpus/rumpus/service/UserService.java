package com.rumpus.rumpus.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rumpus.rumpus.data.IUserDao;
import com.rumpus.rumpus.models.User;

// @Component
public class UserService implements IUserService {

    IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User get(int id) {
        return userDao.get(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.get(name);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public boolean remove(int id) {
        return userDao.remove(id);
    }
    
}
