package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.AuthService;
import com.teamdefinex.dfxsurvey.dto.LoginResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import jakarta.annotation.PostConstruct;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${app.client-id}")
    private String clientId;

    @Value("${app.client-secret:}")
    private String clientSecret;

    @Value("${app.realm:master}")
    private String realm;

    private AuthzClient authzClient;
    private Configuration configuration;

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

        this.configuration = config;
        this.authzClient = AuthzClient.create(config);
    }

    public Result<LoginResponseDTO> login(String username, String password) {
        var tokenResponse = authzClient.obtainAccessToken(username, password);

        var dto = new LoginResponseDTO();
        dto.setAccessToken(tokenResponse.getToken());
        dto.setRefreshToken(tokenResponse.getRefreshToken());

        return Result.success(dto);
    }

    public Result<LoginResponseDTO> refresh(String refreshToken) {
        String url = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        Http http = new Http(configuration, authzClient.getConfiguration().getClientCredentialsProvider());

        AccessTokenResponse tokenResponse = http.<AccessTokenResponse>post(url)
                .authentication()
                .client()
                .form()
                .param("grant_type", "refresh_token")
                .param("refresh_token", refreshToken)
                .param("client_id", clientId)
                .param("client_secret", clientSecret)
                .response()
                .json(AccessTokenResponse.class)
                .execute();

        var dto = new LoginResponseDTO();
        dto.setAccessToken(tokenResponse.getToken());
        dto.setRefreshToken(tokenResponse.getRefreshToken());

        return Result.success(dto);
    }
}
