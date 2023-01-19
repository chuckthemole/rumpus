package com.rumpus.rumpus.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rumpus.rumpus.models.User;

public class UserDaoTest {
    private static IUserDao userDao;
    private static List<User> userList;

    @BeforeAll
    public static void setUpClass() {
        userList = new ArrayList<>();
        userList.add(User.createUser("Frodo"));
        userList.add(User.createUser("Sam"));
        userList.add(User.createUser("Bilbo"));

        userDao = UserDao.create();
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
        System.out.println("* * * Test UserDao * * * ");
        User temp = userDao.get("Frodo");
        System.out.print(temp.toString());
        temp = userDao.get("Sam");
        System.out.print(temp.toString());
        temp = userDao.get("Bilbo");
        System.out.print(temp.toString());
	}
}
