package com.hung.shop.dto.request;

import lombok.Data;

@Data
public class UserAuthCreateDto {
    private String provider;
    private String providerId;
    private String password;
}
