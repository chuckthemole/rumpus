package com.rumpus.rumpus.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserFactory;

public class RumpusUserTest {
    Long id = Long.valueOf(1);
    String name = "Pipin";
    RumpusUserFactory userFactory = new RumpusUserFactory();
    RumpusUser user;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        user = userFactory.createEmpty();
        user.setUsername("Pipin");
        user.setEncodedPassword("marryandpippin");
        user.setEmail("pipin@shire.com");
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testId() {
        assertEquals(name, user.getUsername());
    }
}
