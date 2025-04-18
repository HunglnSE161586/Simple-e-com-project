package com.hung.shop.auth.service;

import com.hung.shop.auth.entity.UserAuth;
import com.hung.shop.share.UserAuthPOJO;

import java.util.List;

public interface IUserAuthService {
    List<UserAuthPOJO> getUserAuthsByUserId(Long userId);
    UserAuthPOJO saveUserAuth(long userId, String password);
}
