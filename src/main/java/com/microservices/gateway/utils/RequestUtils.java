package com.microservices.gateway.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class RequestUtils {

    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> performGet(String url, HttpEntity<?> httpEntity, Class<T> targetResponse) {
        return performRequest(url, HttpMethod.GET, httpEntity, targetResponse);
    }

    public <T> ResponseEntity<T> performPost(String url, HttpEntity<?> httpEntity, Class<T> targetResponse) {
        return performRequest(url, HttpMethod.POST, httpEntity, targetResponse);
    }

    public <T> ResponseEntity<T> performPut(String url, HttpEntity<?> httpEntity, Class<T> targetResponse) {
        return performRequest(url, HttpMethod.PUT, httpEntity, targetResponse);
    }

    private <T> ResponseEntity<T> performRequest(String url, HttpMethod httpMethod,
                                                 HttpEntity<?> httpEntity, Class<T> targetResponse) {
        return restTemplate.exchange(URI.create(url), httpMethod, httpEntity, targetResponse);
    }

}
