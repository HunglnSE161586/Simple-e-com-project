package com.hung.shop.share;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAuthPOJO {
    private Long id;
    private String provider;
    private String providerId;
    private String password;
    private Long userId;
    private LocalDateTime createdAt;
}
