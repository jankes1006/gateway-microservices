package com.microservices.gateway.service.impl;

import com.microservices.gateway.config.security.KeycloakConfig;
import com.microservices.gateway.dto.auth.AuthRequestDto;
import com.microservices.gateway.dto.auth.AuthResponseDto;
import com.microservices.gateway.exception.UnauthorizedException;
import com.microservices.gateway.model.TokenResponse;
import com.microservices.gateway.service.AuthService;
import com.microservices.gateway.utils.HeaderUtils;
import com.microservices.gateway.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.microservices.gateway.utils.KeycloakErrorResolver.getErrorDescription;

@Service
@Slf4j
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {

    private final KeycloakConfig config;
    private final RequestUtils requestUtils;
    private final HeaderUtils headerUtils;

    @Override
    public Mono<AuthResponseDto> authenticate(AuthRequestDto authRequestDto) {
        log.info("authenticate - username: {}", authRequestDto.getUsername());
        final var response = doAuthenticate(authRequestDto);
        final var body = response.getBody();
        return Mono.just(
                AuthResponseDto.builder()
                        .token(body.getAccessToken())
                        .expiresIn(modifyExpirationTimeBy(body.getExpiresIn(), 30))
                        .refreshToken(body.getRefreshToken())
                        .refreshTokenExpiresIn(body.getRefreshExpiresIn())
                        .tokenType(body.getTokenType())
                        .scope(body.getScope())
                        .sessionScope(body.getSessionScope())
                        .build());
    }

    private ResponseEntity<TokenResponse> doAuthenticate(AuthRequestDto authRequestDto) {
        try {
            final var authRequestEntity = headerUtils.createAuthRequestEntity(authRequestDto, config);
            return requestUtils.performPost(config.getTokenUri(), authRequestEntity, TokenResponse.class);
        } catch (HttpClientErrorException e) {
            log.warn(e.getMessage(), e);
            throw new UnauthorizedException(
                    e.getStatusCode(),
                    e.getStatusCode().getReasonPhrase(),
                    getErrorDescription(e.getResponseBodyAsString())
            );
        }
    }

    private Integer modifyExpirationTimeBy(Integer expiresIn, int value) {
        return Optional.ofNullable(expiresIn).map(e -> e - value).orElse(0);
    }

}
