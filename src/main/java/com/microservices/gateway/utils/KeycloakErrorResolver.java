package com.microservices.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.gateway.model.KeycloakError;

public class KeycloakErrorResolver {

    public static KeycloakError getKeycloakError(String json) {
        final var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, KeycloakError.class);
        } catch (JsonProcessingException e) {
            return KeycloakError.empty();
        }
    }

    public static String getError(String json) {
        return getKeycloakError(json).getError();
    }

    public static String getErrorDescription(String json) {
        return getKeycloakError(json).getErrorDescription();
    }

}
