package com.buildshift;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.rumpus.buildshift",
        "com.rumpus.shared"
})
@EnableJpaRepositories(basePackages = "com.rumpus.buildshift.data")
public class BuildShiftApp {
    // Nothing else needed – SpringBootApplication entry point
}
