package com.rumpus.rumpus.database_loader;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.data.IAuthDao;
import com.rumpus.rumpus.data.IRumpusDao;
import com.rumpus.rumpus.data.IRumpusDaoManager;
import com.rumpus.rumpus.data.IUserDao;
import com.rumpus.rumpus.models.Auth;
import com.rumpus.rumpus.models.User;

public class RumpusLoader implements CommandLineRunner {  

    private IRumpusDaoManager dao;
	// private final IUserDao userDao;
    // private final IAuthDao authDao;

	public RumpusLoader(IRumpusDaoManager dao) {
        this.dao = dao;
	}

	@Override
	public void run(String... strings) throws Exception {
        IAuthDao authDao = (IAuthDao) dao.get("auth");
        authDao.add(Auth.createAdminAuth(0));
        IUserDao userDao = (IUserDao) dao.get("user");
        userDao.add(User.createUser(0, "Frodo"));

        // System.out.println("* * Printing Keys * * ");
        // dao.getMap().forEach((k, v) -> {
        //     System.out.println("Key: " + k);
        // });
	}
}
