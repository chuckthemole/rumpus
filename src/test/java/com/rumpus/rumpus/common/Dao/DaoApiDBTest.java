package com.rumpus.rumpus.common.Dao;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumpus.AbstractRumpusTest;
import com.rumpus.common.FileIO.FileProcessor;
import com.rumpus.common.FileIO.IFileIO;
import com.rumpus.common.FileIO.JsonIO;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * TODO: make tests for common dao
 */
public class DaoApiDBTest extends AbstractRumpusDaoTest {

    private final IFileIO fileReader = JsonIO.create();
    private FileProcessor fileProcessor;

    @MockBean private RumpusUserDao userDao;
    private static RumpusUser[] users;

    public DaoApiDBTest() {
        super(DaoApiDBTest.class);
        this.fileProcessor = new FileProcessor(fileReader);
    }

    @Override
    protected void setUp() {
        this.LOG("DaoApiDBTest::setUp()");
        try {
            DaoApiDBTest.users = this.fileProcessor.<RumpusUser>processFile(
                AbstractRumpusTest.JSON_USERS_FILE,
                RumpusUser[].class
            ).get();
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
