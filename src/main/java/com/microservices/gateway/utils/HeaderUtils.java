package com.microservices.gateway.utils;

import com.microservices.gateway.config.security.KeycloakConfig;
import com.microservices.gateway.dto.auth.AuthRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class HeaderUtils {

    private static String CLIENT_ID = "client_id";
    private static String SCOPE = "scope";
    private static String GRANT_TYPE = "grant_type";
    private static String USERNAME = "username";
    private static String PASSWORD = "password";

    public HttpEntity<MultiValueMap<String, String>> createAuthRequestEntity(AuthRequestDto authRequestDto, KeycloakConfig config) {
        return new HttpEntity<>(createAuthBody(authRequestDto, config), createAuthHeaders());
    }

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private MultiValueMap<String, String> createAuthBody(AuthRequestDto authRequestDto, KeycloakConfig config) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(CLIENT_ID, config.getId());
        map.add(SCOPE, config.getScope());
        map.add(GRANT_TYPE, config.getGrantType());
        map.add(USERNAME, authRequestDto.getUsername());
        map.add(PASSWORD, authRequestDto.getPassword());
        return map;
    }
}
