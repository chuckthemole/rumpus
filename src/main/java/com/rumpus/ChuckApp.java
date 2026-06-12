package com.rumpus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.chuck")
@EnableJpaRepositories(basePackages = "com.chuck.data")
public class ChuckApp {
    // Nothing else needed – SpringBootApplication entry point
}
