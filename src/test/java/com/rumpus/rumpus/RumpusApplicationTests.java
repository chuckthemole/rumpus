package com.rumpus.rumpus;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rumpus.rumpus.config.RumpusConfig;
import com.rumpus.rumpus.controller.RumpusRestController;

/**
 * RumpusApplicationTests
 * 
 * This class tests the RumpusRestController class.
 * <p>
 * Note: If this test fails, a common cause is that the properties.yml file is not updated properly. Make sure you are updating the correct values for the correct environment.
 */
@ContextConfiguration(classes = RumpusConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RumpusApplicationTests {
 
  @Autowired
  RumpusRestController rumpusRestController;
 
  @Test
  public void contextLoads() {
    Assertions.assertThat(rumpusRestController).isNotNull();
  }
}