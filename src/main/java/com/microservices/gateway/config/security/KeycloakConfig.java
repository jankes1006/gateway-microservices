package com.microservices.gateway.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak-client")
@Getter
@Setter
public class KeycloakConfig {
    private String serverUrl;
    private String realm;
    private String id;
    private String grantType;
    private String scope;
    private String adminClientId;
    private String adminClientSecret;
    private String adminClientGrantType;
    private String tokenUri;
}
