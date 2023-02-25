// package com.rumpus.rumpus.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.rumpus.common.ActiveUserStore;
// import com.rumpus.common.AuthenticationHandler;
// import com.rumpus.common.LoggedUser;

// @Configuration
// public class ActiveUserConfig {

//     @Bean ActiveUserStore activeUserStore() {
//         return new ActiveUserStore();
//     }

//     @Bean
//     public AuthenticationHandler authenticationHandler() {
//         return new AuthenticationHandler(activeUserStore());
//     }

//     @Bean LoggedUser loggedUser() {
//         return new LoggedUser();
//     }
// }
