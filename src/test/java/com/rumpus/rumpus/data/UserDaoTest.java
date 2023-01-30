package com.rumpus.rumpus.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rumpus.DaoTest;
import com.rumpus.rumpus.config.RumpusTestConfig;
import com.rumpus.rumpus.models.User;

@ContextConfiguration(classes = RumpusTestConfig.class)
// @Import(RumpusTestConfig.class)
// @ExtendWith(SpringExtension.class)
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest extends DaoTest<User> {

    @Autowired
    private static IUserDao userDao;

    // @BeforeAll
    // public static void setUpClass() {
    //     List<User> userList;
    //     userList = new ArrayList<>();
    //     userList.add(User.createWithName("Frodo"));
    //     userList.add(User.createWithName("Sam"));
    //     userList.add(User.createWithName("Bilbo"));

    //     // userDao = UserDao.create();
    //     // userDao = new UserDao();
    //     // userApiDB = new ApiDBJdbc<>(ds, userDao.getTable(), userDao.getMapper());
    //     // userDao.setApiDB(userApiDB);
    //     userDao.add(userList.get(0));
    //     userDao.add(userList.get(1));
    //     userDao.add(userList.get(2));
    // }
    
    // @AfterAll
    // public static void tearDownClass() {
    //     userDao.removeAll();
    // }
    
    // @BeforeEach
    // public void setUp() {
    // }
    
    // @AfterEach
    // public void tearDown() {
    // }

    @Test
	void testManager() {
        List<User> userList;
        userList = new ArrayList<>();
        userList.add(User.createWithName("Frodo"));
        userList.add(User.createWithName("Sam"));
        userList.add(User.createWithName("Bilbo"));

        // userDao = UserDao.create();
        // userDao = new UserDao();
        // userApiDB = new ApiDBJdbc<>(ds, userDao.getTable(), userDao.getMapper());
        // userDao.setApiDB(userApiDB);
        userDao.add(userList.get(0));
        userDao.add(userList.get(1));
        userDao.add(userList.get(2));

        
        LOG.info("* * * Test UserDao * * * ");
        User temp = userDao.get("Frodo");
        LOG.info(temp.toString());
        temp = userDao.get("Sam");
        LOG.info(temp.toString());
        temp = userDao.get("Bilbo");
        LOG.info(temp.toString());
	}
}
