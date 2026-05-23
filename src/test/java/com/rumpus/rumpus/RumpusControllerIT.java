package com.rumpus.rumpus;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RumpusControllerIT {

    // @Autowired
    // private TestRestTemplate template;

    // @Test
    // public void getHello() throws Exception {
    // ResponseEntity<String> response = template.getForEntity("/", String.class);
    // assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot!");
    // }
}
