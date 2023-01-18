package com.rumpus.rumpus.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rumpus.common.Dao;
import com.rumpus.common.Manager;
import com.rumpus.common.RumpusObject;
import com.rumpus.common.util.Pair;

public class RumpusDaoManager extends Manager implements IRumpusDaoManager {
    private UserDao userDao;
    private AuthDao authDao;

    public RumpusDaoManager() {
        super();
        init();
    }

    @Override
    public void init() {
        userDao = UserDao.create();
        authDao = AuthDao.create();
        itemList.add(userDao.name());
        itemList.add(authDao.name());
    }

    public <T extends RumpusObject> T get(String dao, Class<T> type) {
        switch(dao) {
            case "userDao":
                return type.cast(getUserDao());
            case "authDao":
                return type.cast(getAuthDao());
            default:
                System.out.println("Error: No DAO with name " + dao);
        }
        return null;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public AuthDao getAuthDao() {
        return authDao;
    }
}
