package com.rumpus.rumpus.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.rumpus.AbstractRumpusTest;
import com.rumpus.common.FileIO.FileProcessor;
import com.rumpus.common.FileIO.IFileIO;
import com.rumpus.common.FileIO.JsonIO;
import com.rumpus.rumpus.collections.RumpusUserCollection;
import com.rumpus.rumpus.config.RumpusConfig;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;

@ContextConfiguration(classes = {RumpusConfig.class})
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RumpusUserDaoTest extends AbstractDaoTest<RumpusUser> {

    // TODO test methods: get, getAll, add, update, remove, look at IDao for methods to test.
    private final IFileIO fileReader = JsonIO.create();
    private FileProcessor fileProcessor;
    
    public RumpusUserDaoTest() {
        super(RumpusUserDaoTest.class);
        this.fileProcessor = new FileProcessor(fileReader);
    }

    @Autowired
    private IRumpusUserDao userDao;

    private static RumpusUser[] users;

    // these are hardcoded in the db
    private static final String ROOT_USER = "chuckthemole";
    private static final String ROOT_USER_EMAIL = "chuckthemole@gmail.com";
    private static final String ROOT_USER_PASS = "coolpassbro";
    private static final java.util.UUID ROOT_USER_ID = java.util.UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final String SECONDARY_USER = "chuck";
    private static final String SECONDARY_USER_EMAIL = "chuck@gmail.com";
    private static final String SECONDARY_USER_PASS = "supersecretsecret";
    private static final java.util.UUID SECONDARY_USER_ID = java.util.UUID.fromString("22222222-2222-2222-2222-222222222222");

    private static RumpusUser expectedRootUser;
    private static RumpusUser expectedSecondaryUser;

    @Override
    protected void setUpClass() {
    }

    @Override
    protected void tearDownClass() {
    }

    @Override
    protected void setUp() {
        // TODO should clear db before, need to implement removeAll to do this.
        expectedRootUser = RumpusUser.createEmptyUser();
        expectedRootUser.setUsername(ROOT_USER);
        expectedRootUser.setEmail(ROOT_USER_EMAIL);
        expectedRootUser.setPassword(ROOT_USER_PASS);
        expectedRootUser.setId(ROOT_USER_ID);
        expectedSecondaryUser = RumpusUser.createEmptyUser();
        expectedSecondaryUser.setUsername(SECONDARY_USER);
        expectedSecondaryUser.setEmail(SECONDARY_USER_EMAIL);
        expectedSecondaryUser.setPassword(SECONDARY_USER_PASS);
        expectedSecondaryUser.setId(SECONDARY_USER_ID);

        // populate users from file
        RumpusUserDaoTest.users = this.fileProcessor.<RumpusUser>processFile(
            AbstractRumpusTest.JSON_USERS_FILE,
            RumpusUser[].class
        ).get();

        // for(RumpusUser user : users) {
        //     LOG(user.toString());
        // }
    }

    @Override
    protected void tearDown() {
    }

    @Test
    @Order(1)
	void testGetUser() {
        assertEquals(expectedRootUser, this.userDao.getByUsername(ROOT_USER));
	}

    @Test
    @Order(2)
    void testAddUser() {
        for(RumpusUser user : RumpusUserDaoTest.users) {
            // TODO check for duplicate user being added. what does this return? what should it return?
            final String username = user.getUsername();
            if(!username.equals(ROOT_USER) && !username.equals(SECONDARY_USER)) {
                this.userDao.add(user);
                assertEquals(user, this.userDao.getByUsername(user.getUsername()));
            }
        }

    }

    @Test
    @Order(3)
    void testAddUserThatAlreadyExists() {
        for(RumpusUser user : RumpusUserDaoTest.users) {
            assertNull(this.userDao.add(user));
        }
    }

    @Test
    @Order(4)
	void testGetUsers() {
        RumpusUserCollection expected = new RumpusUserCollection(new ArrayList<>(List.of(RumpusUserDaoTest.users))); // TODO: get rid of ArrayList
        RumpusUserCollection actual = new RumpusUserCollection(this.userDao.getAll());
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

        RumpusUserCollection users1 = new RumpusUserCollection(List.of(RumpusUserDaoTest.users));
        RumpusUserCollection users2 = new RumpusUserCollection(List.of(RumpusUserDaoTest.users));
        assertEquals(users1.sortByUsername(), users2.sortByUsername());
        assertNotEquals(users1.sortByEmail(), users2.sortById());
    }

    @Test
    @Order(6)
    void testRemoveUser() {
        for(RumpusUser user : RumpusUserDaoTest.users) {
            final String username = user.getUsername();
            if(!username.equals(ROOT_USER) && !username.equals(SECONDARY_USER)) {
                this.userDao.remove(username);
                assertNull(this.userDao.getByUsername(username));
            }
        }
    }
}
