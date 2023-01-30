package com.rumpus.rumpus.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rumpus.common.DaoTest;
import com.rumpus.common.ApiDB.IApi;
import com.rumpus.common.ApiDB.Jdbc;
import com.rumpus.rumpus.models.User;

public class UserDaoTest extends DaoTest<User> {
    private static IApi<User> userApiDB;
    private static IUserDao userDao;
    private static List<User> userList;
    @Autowired
    private static DataSource ds;

    @BeforeAll
    public static void setUpClass() {
        userList = new ArrayList<>();
        userList.add(User.createWithName("Frodo"));
        userList.add(User.createWithName("Sam"));
        userList.add(User.createWithName("Bilbo"));

        // userDao = UserDao.create();
        userApiDB = new Jdbc<>(ds, UserDao.TABLE, UserDao.mapper());
        userDao = new UserDao(userApiDB);
        userDao.add(userList.get(0));
        userDao.add(userList.get(1));
        userDao.add(userList.get(2));
    }
    
    @AfterAll
    public static void tearDownClass() {
        userDao.removeAll();
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
	void testManager() {
        // LOG.info("* * * Test UserDao * * * ");
        // User temp = userDao.get("Frodo");
        // LOG.info(temp.toString());
        // temp = userDao.get("Sam");
        // LOG.info(temp.toString());
        // temp = userDao.get("Bilbo");
        // LOG.info(temp.toString());
	}
}
