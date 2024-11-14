package com.rumpus.rumpus.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.web.SecurityFilterChain;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.ICommon;
import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.User.ActiveUserStore;
import com.rumpus.common.User.AuthenticationHandler;
import com.rumpus.rumpus.security.Unauthorized;

@Configuration
@EnableWebSecurity // WebSecurityConfiguration
// @PropertySource("classpath:database.properties")
// TODO: Can we abstract this to common?
public class WebSecurityConfig extends AbstractCommonConfig {
    
    @Autowired
    public WebSecurityConfig(Environment environment) {
        super(environment);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
        // Spring Security should completely ignore URLs starting with /resources/
                .requestMatchers("/resources/**");
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception { // allowing 'ADMIN' access to /api/users
        AuthenticationHandler authHandler = new AuthenticationHandler(null); // TODO: look into activeUserStore later. It's causing a problem being set to null right now. 11/13/2024 chuck

        http
            .cors()
            .and()
            // .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).invalidateHttpSession(true).and() TODO: maybe customize later
            .csrf().disable() // need this disabled for signing up 5/5/2023 Chuck
            .formLogin(form -> form
                .loginPage(ICommonController.PATH_INDEX)
                .loginProcessingUrl(ICommonController.PATH_LOGIN)
                .failureHandler(authHandler)
                .successHandler(authHandler)
                // .defaultSuccessUrl(ICommonController.PATH_INDEX, true)
                // .failureForwardUrl(ICommonController.PATH_INDEX).permitAll()
            )
            // .formLogin().failureHandler(failureHandler).loginPage(ICommonController.PATH_INDEX).loginProcessingUrl(ICommonController.PATH_LOGIN).defaultSuccessUrl(ICommonController.PATH_INDEX, true).successForwardUrl(ICommonController.PATH_INDEX).failureForwardUrl(ICommonController.PATH_INDEX).permitAll()
            // .and()
            // .logout().clearAuthentication(true).deleteCookies("remove").invalidateHttpSession(false).logoutUrl(PATH_LOGOUT).logoutSuccessUrl("/logout.done")
            // .and()
            .exceptionHandling()
                .authenticationEntryPoint(new Unauthorized())
            .and()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(ICommonController.PATH_API_USERS).hasRole(ICommon.ADMIN) //only admin should view user list

                // this should prolly be changed when deployed, permitting all for ease of use/testing rn 5/8/2023 chuck
                .requestMatchers("/**").permitAll()
                .anyRequest().permitAll()
                // .anyRequest().authenticated() 
            );
            // .requestMatchers(ICommonController.PATH_API_USERS).hasRole(ROLE_ADMIN)// .anyRequest().authenticated() only admin should view user list
            // .requestMatchers("/**").permitAll();
            // .failureForwardUrl(ICommonController.PATH_LOGIN_FAILURE)
            // .permitAll();
            // .and()
            // .formLogin();

        return http.build();
    }

    // @Bean
    // @DependsOn({"rumpusUserDao"})
    // public AuthenticationManager authenticationManager() {
    //     LOG("WebSecurityConfig::authenticationManager()");
    //     return new RumpusUserAuthenticationManager(this.applicationContext.getBean("rumpusUserDao", IRumpusUserDao.class));
    // }

    // TODO: Look back at this again later https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html
    // @Bean
    // public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    //     DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	// 	authenticationProvider.setUserDetailsService(userDetailsService);
	// 	authenticationProvider.setPasswordEncoder(passwordEncoder);

	// 	ProviderManager providerManager = new ProviderManager(authenticationProvider);
	// 	providerManager.setEraseCredentialsAfterAuthentication(false);

	// 	return providerManager;
    // }

    // @Bean UserDetailsService userDetailsService() {
    //     return new RumpusUserService(this.applicationContext.getBean("rumpusUserDao", IRumpusUserDao.class));
    // }

    // @Bean
	// public PasswordEncoder passwordEncoder() {
	// 	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	// }

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
