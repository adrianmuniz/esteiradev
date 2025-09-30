package com.esteiradev.esteira.client;

import com.esteiradev.esteira.configs.rest.RestTemplateConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@Data
@Service
public class UserClient {

    @Autowired
    RestTemplateConfig restTemplate;

    @Value("${esteira.api.url.authuser}")
    String userServiceUrl;

    public boolean userExists(UUID userId, String authHeader){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = userServiceUrl + "/users/" + userId;
        try {
            restTemplate.restTemplate().exchange(url, HttpMethod.GET, entity, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
