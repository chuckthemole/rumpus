package com.rumpus.rumpus.common.Dao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.google.gson.JsonSyntaxException;
import com.rumpus.RumpusTest;
import com.rumpus.common.util.ReadJson;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser;

/**
 * TODO: make tests for common dao
 */
public class DaoApiDBTest extends CommonDaoTest {

    @MockBean private RumpusUserDao userDao;

    private static RumpusUser[] users;

    @BeforeAll
    public static void setUpClass() throws JsonSyntaxException, Exception {
        ReadJson<RumpusUser> json = new ReadJson<>(RumpusTest.JSON_USERS_FILE, new com.google.gson.reflect.TypeToken<RumpusUser[]>(){}.getType());
        users = json.readModelsFromFile();
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
