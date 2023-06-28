package com.rumpus.rumpus.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rumpus.common.AbstractCommonConfig;
import com.rumpus.common.User.ActiveUserStore;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity // WebSecurityConfiguration
// @PropertySource("classpath:database.properties")
public class WebSecurityConfig extends AbstractCommonConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception { // allowing 'ADMIN' access to /api/users

        AuthenticationFailureHandler failureHandler = new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
            }
            
        };

        http
            .cors()
            .and()
            // .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).invalidateHttpSession(true).and() TODO: maybe customize later
            .csrf().disable() // need this disabled for signing up 5/5/2023 Chuck
            .formLogin(form -> form
                .loginPage(PATH_INDEX)
                .loginProcessingUrl(PATH_LOGIN)
                // .defaultSuccessUrl(PATH_INDEX, true)
                // .failureForwardUrl(PATH_INDEX).permitAll()
            )
            // .formLogin().failureHandler(failureHandler).loginPage(PATH_INDEX).loginProcessingUrl(PATH_LOGIN).defaultSuccessUrl(PATH_INDEX, true).successForwardUrl(PATH_INDEX).failureForwardUrl(PATH_INDEX).permitAll()
            // .and()
            // .logout().clearAuthentication(true).deleteCookies("remove").invalidateHttpSession(false).logoutUrl(PATH_LOGOUT).logoutSuccessUrl("/logout.done")
            // .and()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PATH_API_USERS).hasRole(ADMIN) //only admin should view user list

                // this should prolly be changed when deployed, permitting all for ease of use/testing rn 5/8/2023 chuck
                .requestMatchers("/**").permitAll()
                .anyRequest().permitAll()
                // .anyRequest().authenticated() 
            );
            // .requestMatchers(PATH_API_USERS).hasRole(ROLE_ADMIN)// .anyRequest().authenticated() only admin should view user list
            // .requestMatchers("/**").permitAll();
            // .failureForwardUrl(PATH_LOGIN_FAILURE)
            // .permitAll();
            // .and()
            // .formLogin();

        return http.build();
    }

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }
}
