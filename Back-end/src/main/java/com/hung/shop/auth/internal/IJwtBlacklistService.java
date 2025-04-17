package com.hung.shop.auth.internal;

public interface IJwtBlacklistService {
    void blacklistToken(String token, long expiryInSeconds);
    boolean isBlacklisted(String token);
}
