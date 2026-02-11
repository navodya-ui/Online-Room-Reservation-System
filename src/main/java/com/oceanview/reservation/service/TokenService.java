package com.oceanview.reservation.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    // token -> username
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public String createToken(String username) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, username);
        return token;
    }

    public boolean isValid(String token) {
        return token != null && tokenStore.containsKey(token);
    }

    public String getUsername(String token) {
        return tokenStore.get(token);
    }

    public void logout(String token) {
        if (token != null) tokenStore.remove(token);
    }
}
