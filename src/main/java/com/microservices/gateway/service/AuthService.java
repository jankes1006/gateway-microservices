package com.microservices.gateway.service;

import com.microservices.gateway.dto.auth.AuthRequestDto;
import com.microservices.gateway.dto.auth.AuthResponseDto;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<AuthResponseDto> authenticate(AuthRequestDto authRequestDto);

}
