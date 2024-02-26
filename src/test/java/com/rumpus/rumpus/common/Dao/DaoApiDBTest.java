package com.rumpus.rumpus.common.Dao;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumpus.AbstractRumpusTest;
import com.rumpus.common.util.ReadJson;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser;

/**
 * TODO: make tests for common dao
 */
public class DaoApiDBTest extends AbstractRumpusDaoTest {

    @MockBean private RumpusUserDao userDao;
    private static RumpusUser[] users;

    public DaoApiDBTest() {
        super(DaoApiDBTest.class);
    }

    @Override
    protected void setUp() {
        this.LOG("DaoApiDBTest::setUp()");
        ReadJson<RumpusUser> json = new ReadJson<>(AbstractRumpusTest.JSON_USERS_FILE, new com.google.gson.reflect.TypeToken<RumpusUser[]>(){}.getType());
        try {
            users = json.readModelsFromFile();
        } catch (com.google.gson.JsonSyntaxException e) {
            this.LOG("DaoApiDBTest::setUp()::JsonSyntaxException");
        } catch (java.lang.Exception e) {
            this.LOG("DaoApiDBTest::setUp()::Exception");
        }
    }

    @Override
    protected void tearDown() {
        this.LOG("DaoApiDBTest::tearDown()");
    }

    @Test
    @Order(1)
	void testGetUser() {
	}

    @Test
    @Order(2)
    void testAddUser() {

    }

    @Test
    @Order(3)
    void testAddUserThatAlreadyExists() {
    }

    @Test
    @Order(4)
	void testGetUsers() {
	}

    @Test
    @Order(5)
    void testSortUsers() {
    }

    @Test
    @Order(6)
    void testRemoveUser() {
    }
}
