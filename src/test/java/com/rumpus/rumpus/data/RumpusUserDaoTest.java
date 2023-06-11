package com.rumpus.rumpus.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.google.gson.JsonSyntaxException;

import com.rumpus.DaoTest;
import com.rumpus.common.util.ReadJson;
import com.rumpus.rumpus.collections.RumpusUserCollection;
import com.rumpus.rumpus.config.RumpusTestConfig;
import com.rumpus.rumpus.models.RumpusUser;

@ContextConfiguration(classes = {RumpusTestConfig.class})
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RumpusUserDaoTest extends DaoTest<RumpusUser> {

    // TODO test methods: get, getAll, add, update, remove, look at IDao for methods to test.
    
    @Autowired
    private IRumpusUserDao dao;

    private static RumpusUser[] users;
    private static final String JSON_USERS_FILE = "src/test/java/com/rumpus/rumpus/data/test_data/test_users.json";

    // these are hardcoded in the db
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
    public static void setUpClass() throws JsonSyntaxException, Exception {
        // TODO should clear db before, need to implement removeAll to do this.
        expectedRootUser = new RumpusUser();
        expectedRootUser.setUsername(ROOT_USER);
        expectedRootUser.setEmail(ROOT_USER_EMAIL);
        expectedRootUser.setPassword(ROOT_USER_PASS);
        expectedRootUser.setId(ROOT_USER_ID);
        expectedSecondaryUser = new RumpusUser();
        expectedSecondaryUser.setUsername(SECONDARY_USER);
        expectedSecondaryUser.setEmail(SECONDARY_USER_EMAIL);
        expectedSecondaryUser.setPassword(SECONDARY_USER_PASS);
        expectedSecondaryUser.setId(SECONDARY_USER_ID);

        ReadJson<RumpusUser> json = new ReadJson<>(JSON_USERS_FILE, new com.google.gson.reflect.TypeToken<RumpusUser[]>(){}.getType());
        users = json.readModelsFromFile();
        // for(RumpusUser user : users) {
        //     LOG.info(user.toString());
        // }
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
    @Order(1)
	void testGetUser() {
        // LOG.info(this.dao.get(ROOT_USER).toString());
        assertEquals(expectedRootUser, this.dao.get(ROOT_USER));
	}

    @Test
    @Order(2)
    void testAddUser() {
        for(RumpusUser user : RumpusUserDaoTest.users) {
            // TODO check for duplicate user being added. what does this return? what should it return?
            final String username = user.getUsername();
            if(!username.equals(ROOT_USER) && !username.equals(SECONDARY_USER)) {
                this.dao.add(user);
                assertEquals(user, this.dao.get(user.getUsername()));
            }
        }

    }

    @Test
    @Order(3)
    void testAddUserThatAlreadyExists() {
        for(RumpusUser user : RumpusUserDaoTest.users) {
            assertNull(this.dao.add(user));
        }
    }

    @Test
    @Order(4)
	void testGetUsers() {
        RumpusUserCollection expected = new RumpusUserCollection(new ArrayList<>(List.of(users))); // TODO: get rid of ArrayList
        RumpusUserCollection actual = new RumpusUserCollection(this.dao.getAll());
        assertEquals(expected.sortByUsername(), actual.sortByUsername());
	}

    // TODO: should create expected lists then sort then assertEquals/notEquals
    @Test
    @Order(5)
    void testSortUsers() {
        // RumpusUsersCollection users1 = new RumpusUsersCollection(new ArrayList<>(List.of(users))); // TODO: get rid of ArrayList
        // RumpusUsersCollection users2 = new RumpusUsersCollection(new ArrayList<>(List.of(users))); // TODO: get rid of ArrayList
        // users1.sortById();
        // users2.sortById();

        RumpusUserCollection users1 = new RumpusUserCollection(List.of(users));
        RumpusUserCollection users2 = new RumpusUserCollection(List.of(users));
        assertEquals(users1.sortByUsername(), users2.sortByUsername());
        assertNotEquals(users1.sortByEmail(), users2.sortById());
    }

    @Test
    @Order(6)
    void testRemoveUser() {
        for(RumpusUser user : RumpusUserDaoTest.users) {
            final String username = user.getUsername();
            if(!username.equals(ROOT_USER) && !username.equals(SECONDARY_USER)) {
                this.dao.remove(username);
                assertNull(this.dao.get(username));
            }
        }
    }
}
