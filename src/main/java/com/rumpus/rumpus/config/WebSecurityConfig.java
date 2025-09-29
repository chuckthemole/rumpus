package com.rumpus.rumpus.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.ICommon;
import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Config.SuccessFailureHandler.AbstractFailureHandler;
import com.rumpus.common.Config.SuccessFailureHandler.AbstractSuccessHandler;
import com.rumpus.common.User.ActiveUserStore;
import com.rumpus.common.User.AuthenticationHandler;
import com.rumpus.rumpus.config.SuccessFailureHandlers.OAuth2Failure;
import com.rumpus.rumpus.config.SuccessFailureHandlers.OAuth2Success;
import com.rumpus.rumpus.security.Unauthorized;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity // WebSecurityConfiguration
// @PropertySource("classpath:database.properties")
// TODO: Can we abstract this to common?
public class WebSecurityConfig extends AbstractCommonConfig {

    // TODO: can maybe make these Beans that have depend injection using Autowire -
    // chuck
    private AbstractFailureHandler oauth2FailureHandler = OAuth2Failure.create();
    private AbstractSuccessHandler oauth2SuccessHandler = OAuth2Success.create();

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
        AuthenticationHandler authHandler = new AuthenticationHandler(null); // TODO: look into activeUserStore later.
                                                                             // It's causing a problem being set to null
                                                                             // right now. 11/13/2024 chuck

        http
                .cors()
                .and()
                // .logout().logoutRequestMatcher(new
                // AntPathRequestMatcher("/logout")).invalidateHttpSession(true).and() TODO:
                // maybe customize later
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            if (authentication == null) {
                                // Could log a warning, return 400, etc.
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.getWriter().write("{\"message\": \"No active session\"}");
                            } else {
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.getWriter().write("{\"message\": \"Logged out successfully\"}");
                            }
                            response.setContentType("application/json");
                        })
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .csrf().disable() // need this disabled for signing up 5/5/2023 Chuck
                .formLogin(form -> form
                        // .loginPage("/")
                        // .loginProcessingUrl(ICommonController.PATH_LOGIN)
                        .loginProcessingUrl("/auth/login") // <- matches frontend call
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        .permitAll()
                // .failureHandler(authHandler)
                // .successHandler(authHandler)
                // .defaultSuccessUrl(ICommonController.PATH_INDEX, true)
                // .failureForwardUrl(ICommonController.PATH_INDEX).permitAll()
                )
                // .formLogin().failureHandler(failureHandler).loginPage(ICommonController.PATH_INDEX).loginProcessingUrl(ICommonController.PATH_LOGIN).defaultSuccessUrl(ICommonController.PATH_INDEX,
                // true).successForwardUrl(ICommonController.PATH_INDEX).failureForwardUrl(ICommonController.PATH_INDEX).permitAll()
                // .and()
                // .logout().clearAuthentication(true).deleteCookies("remove").invalidateHttpSession(false).logoutUrl(PATH_LOGOUT).logoutSuccessUrl("/logout.done")
                // .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Unauthorized())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ICommonController.PATH_API_USERS).hasRole(ICommon.ADMIN) // only admin should
                                                                                                  // view user list

                        // this should prolly be changed when deployed, permitting all for ease of
                        // use/testing rn 5/8/2023 chuck
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/api/auth/**", "/oauth2/**", "/login/oauth2/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .anyRequest().permitAll()
                // .anyRequest().authenticated()
                ).oauth2Login(oauth2 -> oauth2
                        .successHandler(this.oauth2SuccessHandler)
                        .failureHandler(this.oauth2FailureHandler));
        // .requestMatchers(ICommonController.PATH_API_USERS).hasRole(ROLE_ADMIN)//
        // .anyRequest().authenticated() only admin should view user list
        // .requestMatchers("/**").permitAll();
        // .failureForwardUrl(ICommonController.PATH_LOGIN_FAILURE)
        // .permitAll();
        // .and()
        // .formLogin();

        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                ClientRegistration.withRegistrationId("custom")
                        .clientId(environment.getProperty(OAUTH2_GOOGLE_CLIENT_ID))
                        .clientSecret(environment.getProperty(OAUTH2_GOOGLE_CLIENT_SECRET))
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .redirectUri("https://localhost:8888/login/oauth2/code/{registrationId}")
                        .scope("read", "write")
                        .authorizationUri("https://provider.com/oauth2/authorize")
                        .tokenUri("https://provider.com/oauth2/token")
                        .clientName("Custom Provider")
                        .build());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(Environment environment) {
        CorsConfiguration configuration = new CorsConfiguration();

        // Get origins as comma-separated string
        String origins = environment.getProperty(AbstractCommonConfig.CORS_ALLOWED_FRONTEND_ORIGINS);
        if (origins != null && !origins.isBlank()) {
            configuration.setAllowedOrigins(Arrays.asList(origins.split(",")));
        } else { // fallback
            configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        }

        // Methods
        String methods = environment.getProperty(AbstractCommonConfig.CORS_ALLOWED_FRONTEND_ALLOWED_METHODS);
        if (methods != null && !methods.isBlank()) {
            configuration.setAllowedMethods(Arrays.asList(methods.split(",")));
        } else {
            configuration.setAllowedMethods(List.of("GET"));
        }

        // Headers
        String headers = environment.getProperty(AbstractCommonConfig.CORS_ALLOWED_FRONTEND_HEADERS);
        if (headers != null && !headers.isBlank()) {
            configuration.setAllowedHeaders(Arrays.asList(headers.split(",")));
        }

        // Credentials
        Boolean credentials = environment.getProperty(
                AbstractCommonConfig.CORS_ALLOWED_FRONTEND_CREDENTIALS,
                Boolean.class);
        if (credentials != null) {
            configuration.setAllowCredentials(credentials);
        } else {
            configuration.setAllowCredentials(false);
        }

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // @Bean
    // @DependsOn({"rumpusUserDao"})
    // public AuthenticationManager authenticationManager() {
    // LOG("WebSecurityConfig::authenticationManager()");
    // return new
    // RumpusUserAuthenticationManager(this.applicationContext.getBean("rumpusUserDao",
    // IRumpusUserDao.class));
    // }

    // TODO: Look back at this again later
    // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html
    // @Bean
    // public AuthenticationManager authenticationManager(UserDetailsService
    // userDetailsService, PasswordEncoder passwordEncoder) {
    // DaoAuthenticationProvider authenticationProvider = new
    // DaoAuthenticationProvider();
    // authenticationProvider.setUserDetailsService(userDetailsService);
    // authenticationProvider.setPasswordEncoder(passwordEncoder);

    // ProviderManager providerManager = new
    // ProviderManager(authenticationProvider);
    // providerManager.setEraseCredentialsAfterAuthentication(false);

    // return providerManager;
    // }

    // @Bean UserDetailsService userDetailsService() {
    // return new RumpusUserService(this.applicationContext.getBean("rumpusUserDao",
    // IRumpusUserDao.class));
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
