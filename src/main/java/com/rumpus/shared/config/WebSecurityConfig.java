package com.rumpus.shared.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;

import java.util.Collections;
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
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.ICommon;
import com.rumpus.common.Config.AbstractCommonConfig;
import com.rumpus.common.Config.SuccessFailureHandler.AbstractFailureHandler;
import com.rumpus.common.Config.SuccessFailureHandler.AbstractSuccessHandler;
import com.rumpus.common.User.ActiveUserStore;
import com.rumpus.common.User.AuthenticationHandler;
import com.rumpus.rumpus.config.SuccessFailureHandlers.OAuth2Failure;
import com.rumpus.rumpus.config.SuccessFailureHandlers.OAuth2Success;
import com.rumpus.rumpus.security.Unauthorized;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;

/**
 * WebSecurityConfig
 * ------------------------------------------------------------
 * Main Spring Security configuration for the Rumpus application.
 * Handles authentication, authorization, logout, and CORS setup.
 * Uses Spring Boot Binder for YAML-driven CORS configuration.
 *
 * TODO:
 * - Consolidate postConstructDebug into AbstractCommonConfig for
 * shared diagnostic logging across all configuration classes.
 * ------------------------------------------------------------
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends AbstractCommonConfig {

    private static final String FRONTEND_LOCAL_HOST = "http://localhost:3000";

    /** Accumulates bean initialization debug info for PostConstruct logging. */
    private final StringBuilder postConstructDebug = new StringBuilder();

    /** OAuth2 handlers (using custom success/failure implementations). */
    private final AbstractFailureHandler oauth2FailureHandler = OAuth2Failure.create();
    private final AbstractSuccessHandler oauth2SuccessHandler = OAuth2Success.create();

    @Autowired
    public WebSecurityConfig(Environment environment) {
        super(environment);
    }

    // ============================================================
    // Security Core Configuration
    // ============================================================

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Completely ignore static resources for performance
        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }

    /**
     * Defines Spring Security's primary filter chain:
     * - Configures form login, logout, and exception handling.
     * - Sets authentication and authorization rules.
     * - Integrates OAuth2 login and handlers.
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationHandler authHandler = new AuthenticationHandler(null);

        http
                .cors().and()
                .csrf().disable() // Disable for form-based login (temporary)
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_OK);
                            if (authentication == null) {
                                response.getWriter().write("{\"message\": \"No active session\"}");
                            } else {
                                response.getWriter().write("{\"message\": \"Logged out successfully\"}");
                            }
                        })
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .formLogin(form -> form
                        .loginProcessingUrl("/auth/login") // Matches frontend call
                        .successHandler((request, response, authentication) -> {
                            LOG(WebSecurityConfig.class, LogLevel.DEBUG,
                                    "User logged in: " + authentication.getName());
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .failureHandler((request, response, exception) -> {
                            LOG(WebSecurityConfig.class, LogLevel.WARN,
                                    "Login failed: " + exception.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        .permitAll())
                .exceptionHandling()
                .authenticationEntryPoint(new Unauthorized())
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ICommonController.PATH_API_USERS).hasRole(ICommon.ADMIN)
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/api/auth/**", "/oauth2/**", "/login/oauth2/**").permitAll()
                        .requestMatchers("/api/public/**", "/**").permitAll()
                // .anyRequest().authenticated() // enable when locking down API
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(this.oauth2SuccessHandler)
                        .failureHandler(this.oauth2FailureHandler));

        return http.build();
    }

    // ============================================================
    // OAuth2 Client Configuration
    // ============================================================

    /**
     * Defines an in-memory OAuth2 client for demonstration or local development.
     * Replace with a real provider configuration for production.
     */
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

    // ============================================================
    // CORS Configuration
    // ============================================================

    /**
     * Configures CORS using Binder to properly parse YAML list properties.
     * Adds all discovered configuration information to postConstructDebug.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(Environment environment) {
        CorsConfiguration configuration = new CorsConfiguration();
        Binder binder = Binder.get(environment);

        // --- Allowed Origins ---
        List<String> origins = bindList(binder, AbstractCommonConfig.CORS_ALLOWED_FRONTEND_ORIGINS);
        configuration.setAllowedOrigins(!origins.isEmpty() ? origins : List.of(FRONTEND_LOCAL_HOST));

        // --- Allowed Methods ---
        List<String> methods = bindList(binder, AbstractCommonConfig.CORS_ALLOWED_FRONTEND_ALLOWED_METHODS);
        configuration.setAllowedMethods(!methods.isEmpty()
                ? methods
                : List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // --- Allowed Headers ---
        List<String> headers = bindList(binder, AbstractCommonConfig.CORS_ALLOWED_FRONTEND_HEADERS);
        configuration.setAllowedHeaders(!headers.isEmpty() ? headers : List.of("*"));

        // --- Allow Credentials ---
        Boolean credentials = binder
                .bind(AbstractCommonConfig.CORS_ALLOWED_FRONTEND_CREDENTIALS, Boolean.class)
                .orElse(true);
        configuration.setAllowCredentials(credentials);

        postConstructDebug.append("Loaded CORS Configuration:\n")
                .append(configuration)
                .append("\n");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Utility to safely bind YAML list properties and record diagnostic details.
     */
    private List<String> bindList(Binder binder, String propertyKey) {
        try {
            List<String> list = binder.bind(propertyKey, Bindable.listOf(String.class))
                    .orElse(Collections.emptyList());
            if (!list.isEmpty()) {
                postConstructDebug.append(String.format("Read property '%s': %s%n", propertyKey, list));
            } else {
                postConstructDebug.append(String.format("Property '%s' missing or empty%n", propertyKey));
            }
            return list;
        } catch (Exception e) {
            postConstructDebug.append(String.format("Error reading '%s': %s%n", propertyKey, e.getMessage()));
            return Collections.emptyList();
        }
    }

    // ============================================================
    // Supporting Beans
    // ============================================================

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    // ============================================================
    // PostConstruct Diagnostics
    // ============================================================

    /**
     * PostConstruct runs after all beans in this config are created.
     * Ideal place to log diagnostic output collected during bean creation.
     * TODO: Move this to AbstractCommonConfig for centralized use.
     */
    @PostConstruct
    public void logPostConstructStatus() {
        LOG(WebSecurityConfig.class, LogLevel.INFO, "===== WebSecurityConfig PostConstruct =====");
        LOG(WebSecurityConfig.class, LogLevel.INFO, postConstructDebug.toString());
    }

    // ============================================================
    // Logging Utility
    // ============================================================

    private static void LOG_THIS(String... msg) {
        LOG(WebSecurityConfig.class, LogLevel.DEBUG, msg);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
