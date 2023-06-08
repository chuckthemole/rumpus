package com.rumpus.rumpus.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
// import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rumpus.DaoTest;
import com.rumpus.rumpus.config.RumpusConfig;
import com.rumpus.rumpus.models.RumpusUser;

@ContextConfiguration(classes = {RumpusConfig.class})
// @SpringBootTest
// @RunWith(SpringJUnit4ClassRunner.class)
public class RumpusUserDaoTest extends DaoTest<RumpusUser> {

    // TODO test methods: get, getAll, add, update, remove, look at IDao for methods to test.
    
    @Autowired
    private static IRumpusUserDao dao;

    private static final String ROOT_USER = "chuckthemole";
    private static final String ROOT_USER_EMAIL = "chuckthemole@gmail.com";
    private static final String ROOT_USER_PASS = "coolpassbro";
    private static final String ROOT_USER_ID = "1111111111";
    private static final String SECONDARY_USER = "chuck";
    private static final String SECONDARY_USER_EMAIL = "chuck@gmail.com";
    private static final String SECONDARY_USER_PASS = "supersecretsecret";
    private static final String SECONDARY_USER_ID = "2222222222";

    private static RumpusUser expectedRootUser;
    private static RumpusUser expectedSecondaryUser;

    @BeforeAll
    public static void setUpClass() {
        expectedRootUser = new RumpusUser();
        expectedRootUser.setUsername(ROOT_USER);
        expectedRootUser.setEmail(ROOT_USER_EMAIL);
        expectedRootUser.setPassword(ROOT_USER_PASS);
        expectedSecondaryUser = new RumpusUser();
        expectedSecondaryUser.setUsername(SECONDARY_USER);
        expectedSecondaryUser.setEmail(SECONDARY_USER_EMAIL);
        expectedSecondaryUser.setPassword(SECONDARY_USER_PASS);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
	void testGetUser() {
        LOG.info(dao.get(ROOT_USER).toString());
        assertEquals(expectedRootUser, dao.get(ROOT_USER));
	}
}
