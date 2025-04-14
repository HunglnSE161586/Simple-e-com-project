package com.hung.shop.dto.respond;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
}
