package com.rumpus.rumpus.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

import com.rumpus.common.ActiveUserStore;
import com.rumpus.common.CommonConfig;

@Configuration
@EnableWebSecurity //WebSecurityConfiguration
// @PropertySource("classpath:database.properties")
public class WebSecurityConfig extends CommonConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception { // allowing 'ADMIN' access to /api/users
        http.cors().and()
            .csrf().disable().authorizeHttpRequests()
            .requestMatchers(PATH_API_USERS).hasRole(ROLE_ADMIN)
            .anyRequest().authenticated()
            .and()
            .formLogin();
         return http.build();
    }
    
    @Bean
    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();
    }
}
