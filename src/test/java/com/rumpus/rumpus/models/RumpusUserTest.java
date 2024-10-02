package com.rumpus.rumpus.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.rumpus.rumpus.models.RumpusUser.RumpusUser;

import static org.junit.jupiter.api.Assertions.*;

public class RumpusUserTest {
    Long id = Long.valueOf(1);
    String name = "Pipin";
    RumpusUser user = RumpusUser.create("Pipin", "marryandpippin","pipin@shire.com");

    @BeforeAll
    public static void setUpClass() {
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
	void testId() {
        assertEquals(name, user.getUsername());
	}
}
