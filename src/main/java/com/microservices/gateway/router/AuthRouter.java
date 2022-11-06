package com.microservices.gateway.router;

import com.microservices.gateway.dto.auth.AuthRequestDto;
import com.microservices.gateway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.microservices.gateway.consts.Endpoints.AUTH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
public class AuthRouter {

    private final AuthService authService;

    @Bean
    public RouterFunction<ServerResponse> authRoutes() {
        return route(POST(AUTH),
                request -> request.bodyToMono(AuthRequestDto.class)
                        .flatMap(authService::authenticate)
                        .flatMap(authResponseDto -> ok().body(BodyInserters.fromValue(authResponseDto))));
    }
}
