package com.rumpus.rumpus.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.rumpus.rumpus.models.Auth.Level;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {
    Long id = Long.valueOf(1);
    private Level level = Level.ADMIN;
    private Auth auth = Auth.createAdminAuth();

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
        System.out.println("* * * Test Id * * * ");
        assertEquals(level, auth.getLevel());
	}
}
