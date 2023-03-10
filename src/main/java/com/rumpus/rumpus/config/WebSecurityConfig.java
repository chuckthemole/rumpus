package com.rumpus.rumpus.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

import com.rumpus.common.ActiveUserStore;
import com.rumpus.common.Session.SecurityManager;

@Configuration
@EnableWebSecurity //WebSecurityConfiguration
public class WebSecurityConfig {

    @Autowired
	public DataSource dataSource;

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.jdbcAuthentication().dataSource(dataSource)
    //         .withDefaultSchema()
    //         .withUser("derek").password(passwordEncoder().encode("love")).roles("USER")
    //         .and()
    //         .withUser("chuck").password(passwordEncoder().encode("shelly")).roles("USER", "ADMIN");
    // }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception { // allowing 'manager' access to /users
        http.cors().and()
            .csrf().disable().authorizeHttpRequests()
            .requestMatchers("/users").hasRole("manager")
            .anyRequest().authenticated()
            .and()
            .formLogin();
         return http.build();
    }

    // @Bean
    // public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
    //     UserDetails user = User.withUsername("chuck")
    //         .password(passwordEncoder.encode("shelly"))
    //         .roles("ADMIN")
    //         .build();
    //     return new InMemoryUserDetailsManager(user);
    // }

    // @Bean
    // public UserDetailsManager userDetails(PasswordEncoder passwordEncoder, DataSource dataSource) {
    //     UserDetails user = User.withUsername("chuck")
    //         .password(passwordEncoder.encode("shelly"))
    //         .roles("ADMIN")
    //         .build();
    //     JdbcUserDetailsManager manager = SecurityManager.createUserManager(dataSource);
    //     manager.createUser(user);
    //     return manager;
    // }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.httpBasic()
    //         .and()
    //         .authorizeHttpRequests()
    //         .requestMatchers("/")
    //         .hasRole("ADMIN")
    //         .anyRequest()
    //         .authenticated();
    //     return http.build();
    // }
	
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager()
	{
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

		jdbcUserDetailsManager.setDataSource(dataSource);
		
		return jdbcUserDetailsManager;
	}

    @Bean 
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }
    
    @Bean
    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();
    }
}
