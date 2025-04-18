package com.hung.shop.user.dto.request;

import com.hung.shop.auth.dto.request.UserAuthCreateDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class UserCreateRequest {
    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    private String email;
    private String firstName;
    private String lastName;
//    private UserAuthCreateDto userAuthCreateDto;
    private String password;
}
