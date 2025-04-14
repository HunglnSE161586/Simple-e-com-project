package com.hung.shop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class UserCreateDto {
    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    private String email;
    private String firstName;
    private String lastName;
    private UserAuthCreateDto userAuthCreateDto;

}
