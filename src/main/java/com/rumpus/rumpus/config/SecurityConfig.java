// package com.rumpus.rumpus.config;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Autowired
//     private DataSource dataSource;

//     @Autowired
//     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//         auth.jdbcAuthentication().dataSource(dataSource)
//             .withDefaultSchema()
//             .withUser("derek").password(passwordEncoder().encode("love")).roles("USER")
//             .and()
//             .withUser("chuck").password(passwordEncoder().encode("shelly")).roles("USER", "ADMIN");
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }
