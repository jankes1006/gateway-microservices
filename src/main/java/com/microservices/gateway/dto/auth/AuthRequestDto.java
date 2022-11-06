package com.microservices.gateway.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {

    private String username;
    private String password;

    public static AuthRequestDto of(String username, String password) {
        return new AuthRequestDto(username, password);
    }

}
