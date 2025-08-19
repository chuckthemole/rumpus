package com.rumpus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.rumpus")
@EnableJpaRepositories(basePackages = "com.rumpus.rumpus.data")
public class RumpusApp {
    // Nothing else needed – SpringBootApplication entry point
}
