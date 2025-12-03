package com.teamdefinex.dfxsurvey.application;

import jakarta.annotation.PostConstruct;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${app.client-id}")
    private String clientId;

    @Value("${app.client-secret:}")
    private String clientSecret;

    @Value("${app.realm:master}")
    private String realm;

    private AuthzClient authzClient;

    @PostConstruct
    public void init() {
        Map<String, Object> credentials = new HashMap<>();
        if (clientSecret != null && !clientSecret.isBlank()) {
            credentials.put("secret", clientSecret);
        }

        Configuration config = new Configuration(
                serverUrl,
                realm,
                clientId,
                credentials,
                null
        );

        this.authzClient = AuthzClient.create(config);
    }

    // TODO!
    public Object login(String username, String password) {
        var tokenResponse = authzClient.obtainAccessToken(username, password);

        return tokenResponse;
    }
}
