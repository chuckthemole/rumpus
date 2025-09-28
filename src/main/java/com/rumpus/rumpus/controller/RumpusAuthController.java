package com.rumpus.rumpus.controller;

import com.rumpus.common.Auth.OAuth2Provider;
import com.rumpus.common.Controller.AbstractAuthController;
import com.rumpus.common.Service.JwtService;
import com.rumpus.common.util.Map.MapStringObject;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.RumpusServiceManager;
import com.rumpus.rumpus.views.RumpusAdminUserView;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@RestController // TODO: start here maybe. I changed from Controller.
public class RumpusAuthController extends
        AbstractAuthController<RumpusServiceManager, RumpusUser, RumpusUserMetaData, IRumpusUserService, RumpusAdminUserView> {

    @Autowired
    public RumpusAuthController(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    private static final Logger logger = LoggerFactory.getLogger(AbstractAuthController.class);

    // @Autowired
    // @Value(AbstractCommonConfig.JWT_SECRET_VALUE_ANNOTATION)
    // private String jwtSecret;

    // @Value(AbstractCommonConfig.JWT_SECRET_EXPIRATION_ANNOTATION)
    // private long jwtExpiration;

    @Autowired
    private JwtService jwtService;

    @Value("${properties.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${properties.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    // @Value("${properties.oauth2.client.registration.github.client-id}")
    // private String githubClientId;

    // @Value("${properties.oauth2.client.registration.github.client-secret}")
    // private String githubClientSecret;

    @Value("${properties.app.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // private RumpusAuthController() {
    // this.jwtService = JwtService.create(this.jwtSecret, this.jwtExpiration);
    // }

    @Override
    protected String buildAuthorizationUrl(OAuth2Provider provider, HttpServletRequest request) {
        String clientId = getClientId(provider);
        String redirectUri = baseUrl + provider.getCallbackPath();
        String state = generateState(); // Store this in session/cache for validation

        return provider.getAuthorizationUrl() +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=" + provider.getDefaultScopes() +
                "&response_type=code" +
                "&state=" + state;
    }

    @Override
    protected String exchangeCodeForToken(OAuth2Provider provider, String code) throws Exception {
        logger.info("exchangeCodeForToken");
        String clientId = getClientId(provider);
        String clientSecret = getClientSecret(provider);
        String redirectUri = baseUrl + provider.getCallbackPath();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        logger.info("request: ", request.toString());

        // ResponseEntity<Map> response = restTemplate.postForEntity(
        // provider.getTokenUrl(), request, Map.class);
        // logger.info("response: ", response.toString());

        // Map<String, Object> responseBody = response.getBody();
        // logger.info("response body: ", responseBody.toString());
        // return (String) responseBody.get("access_token");

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                provider.getTokenUrl(),
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

        Map<String, Object> responseBody = response.getBody();
        logger.info("response body: ", responseBody.toString());
        return (String) responseBody.get("access_token");
    }

    @Override
    protected Map<String, Object> getUserInfo(OAuth2Provider provider, String accessToken) throws Exception {
        logger.info("getUserInfo");
        if (provider.getUserInfoUrl() == null) {
            logger.info("provider user info is null");
            return new HashMap<>(); // For providers like Apple that don't have separate user info endpoint
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        logger.info("accessToken: ", accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // ResponseEntity<Map> response = restTemplate.exchange(
        // provider.getUserInfoUrl(), HttpMethod.GET, entity, Map.class);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                provider.getUserInfoUrl(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

        logger.info("response: ", response.toString());

        return response.getBody();
    }

    @Override
    protected ResponseEntity<?> handleUserAuthentication(
            OAuth2Provider provider,
            Map<String, Object> userInfo,
            String accessToken) {
        try {
            // Generate JWT token using your JwtService
            String jwtToken = jwtService.generateToken(provider, userInfo);

            // Extract user details for response
            String email = provider.extractEmail(userInfo);
            String name = provider.extractName(userInfo);
            String picture = provider.extractPicture(userInfo);

            // Here you could also:
            // - Save/update user in your database
            // - Log the authentication event
            // - Set additional claims or permissions

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("user", Map.of(
                    "email", email,
                    "name", name,
                    "picture", picture,
                    "provider", provider.getProviderId()));

            logger.info(MapStringObject.toString(response));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error handling user auth");
            return ResponseEntity.badRequest()
                    .body("Failed to authenticate user: " + e.getMessage());
        }
    }

    private String getClientId(OAuth2Provider provider) {
        switch (provider) {
            case GOOGLE:
                return googleClientId;
            // case GITHUB:
            // return githubClientId;
            // Add other providers as needed
            default:
                throw new IllegalArgumentException("Client ID not configured for " + provider);
        }
    }

    private String getClientSecret(OAuth2Provider provider) {
        switch (provider) {
            case GOOGLE:
                return googleClientSecret;
            // case GITHUB:
            // return githubClientSecret;
            // Add other providers as needed
            default:
                throw new IllegalArgumentException("Client secret not configured for " + provider);
        }
    }

    private String generateState() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}