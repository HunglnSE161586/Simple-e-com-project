package com.hung.shop.share;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UsersPOJO {
    private Long id;
    private String email;

    private String firstName;

    private String lastName;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserRolePOJO userRolePOJO;
    private List<UserAuthPOJO> userAuthPOJO;
//    private String roleName;
}
