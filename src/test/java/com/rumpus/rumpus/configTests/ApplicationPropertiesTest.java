package com.rumpus.rumpus.configTests;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rumpus.RumpusTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationPropertiesTest extends RumpusTest {

    @Autowired
    private com.rumpus.rumpus.config.ApplicationProperties applicationProperties;

    @Test
    public void whenYamlApplicationPropertiesProvidedThenReadProperties() {
        assertEquals("RumpusTest", applicationProperties.getName());
        assertEquals(java.util.Arrays.asList("abcTest", "xyzTest"), applicationProperties.getAliases());
    }

}
