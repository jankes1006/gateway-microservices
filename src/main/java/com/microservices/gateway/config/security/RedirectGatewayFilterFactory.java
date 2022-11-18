package com.microservices.gateway.config.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RedirectGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private String basePath = "";

    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            var req = exchange.getRequest();
            var path = req.getURI().getRawPath();
            var newPath = path.replaceFirst(basePath, StringUtils.EMPTY);
            ServerHttpRequest request = req.mutate().path(newPath).contextPath(null).build();
            return chain.filter(exchange.mutate().request(request).build());
        }, 1);
    }
}
