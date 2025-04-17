package com.hung.shop.auth.service;

import com.hung.shop.auth.internal.IJwtBlacklistService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class JwtBlacklistService implements IJwtBlacklistService {
    private final RedisTemplate<String, String> redisTemplate;

    public void blacklistToken(String token, long expiryInSeconds) {

        //Key: token → your JWT string (acts like a key in Redis).
        //
        //Value: "blacklisted" → a dummy value, because we really only care that this key exists.
        //
        //Expiration: expirationSeconds → how long until this key auto-deletes.
        //
        //TimeUnit: TimeUnit.SECONDS → so expirationSeconds is treated in seconds.

        redisTemplate.opsForValue().set(token, "blacklisted", expiryInSeconds, TimeUnit.SECONDS);
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
