package com.hung.shop.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserAuthCreateDto {

    private String provider;
    private String providerId;
    private String password;
//    private Long userId;
}
