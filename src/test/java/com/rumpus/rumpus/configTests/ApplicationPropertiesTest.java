package com.rumpus.rumpus.configTests;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rumpus.AbstractRumpusTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationPropertiesTest extends AbstractRumpusTest {

    @Autowired
    private com.rumpus.rumpus.config.ApplicationProperties applicationProperties;

    public ApplicationPropertiesTest() {
        super(ApplicationPropertiesTest.class);
    }

    @Override
    protected void setUpClass() {
    }

    @Override
    protected void tearDownClass() {
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    @Test
    public void whenYamlApplicationPropertiesProvidedThenReadProperties() {
        assertEquals("RumpusTest", applicationProperties.getName());
        assertEquals(java.util.Arrays.asList("abcTest", "xyzTest"), applicationProperties.getAliases());
    }
}
