package com.rumpus.rumpus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RumpusPropertiesTest {

    @Autowired
    private Environment environment;

    @Test
    void testSimpleProperty() {
        String appName = environment.getProperty("properties.name");
        assertEquals("RumpusTest", appName);
        System.out.println("✅ properties.name: " + appName);
    }

    @Test
    void testListProperty() {
        String[] tags = environment.getProperty("properties.frontend.origins[0]", String[].class);
        assertNotNull(tags);
        assertEquals(2, tags.length);
        assertEquals("http://localhost:3000", tags[0]);
        System.out.println("✅ properties.frontend.origins: " + Arrays.toString(tags));
    }
}

