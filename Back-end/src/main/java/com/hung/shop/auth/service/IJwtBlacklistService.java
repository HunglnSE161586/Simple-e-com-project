package com.hung.shop.auth.service;

public interface IJwtBlacklistService {
    void blacklistToken(String token, long expiryInSeconds);
    boolean isBlacklisted(String token);
}
