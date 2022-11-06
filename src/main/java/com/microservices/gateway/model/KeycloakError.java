package com.microservices.gateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakError {

    private String error;
    @JsonProperty("error_description")
    private String errorDescription;

    public static KeycloakError empty() {
        return new KeycloakError(StringUtils.EMPTY, StringUtils.EMPTY);
    }
}
