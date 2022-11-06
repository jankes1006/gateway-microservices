package com.microservices.gateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenResponse {

    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;
    @JsonProperty(value = "refresh_expires_in")
    private Integer refreshExpiresIn;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "token_type")
    private String tokenType;
    @JsonProperty(value = "id_token")
    private String idToken;
    @JsonProperty(value = "not-before-policy")
    private Integer notBeforePolicy;
    @JsonProperty(value = "session_state")
    private String sessionScope;
    @JsonProperty(value = "scope")
    private String scope;

}
