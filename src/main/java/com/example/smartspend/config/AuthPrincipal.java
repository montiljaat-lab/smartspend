package com.example.smartspend.config;

public class AuthPrincipal {
    private final Long userId;
    public AuthPrincipal(Long id){ this.userId = id; }
    public Long getUserId(){ return userId; }
}