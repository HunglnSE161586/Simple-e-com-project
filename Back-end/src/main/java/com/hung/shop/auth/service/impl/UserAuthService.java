package com.hung.shop.auth.service.impl;

import com.hung.shop.auth.entity.UserAuth;
import com.hung.shop.auth.mapper.UserAuthMapper;
import com.hung.shop.auth.repository.UserAuthRepository;
import com.hung.shop.auth.service.IUserAuthService;
import com.hung.shop.share.UserAuthPOJO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthService implements IUserAuthService {
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private UserAuthRepository userAuthRepository;
    public List<UserAuthPOJO> getUserAuthsByUserId(Long userId) {
        return userAuthRepository.findAll().stream()
                .filter(userAuth -> userAuth.getUserId().equals(userId))
                .map(userAuthMapper::toPOJO)
                .toList();
    }

    @Override
    @Transactional
    public UserAuthPOJO saveUserAuth(long userId, String password) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(userId);
        userAuth.setPassword(password);
        userAuth.setProvider("local");
        return userAuthMapper.toPOJO(userAuthRepository.save(userAuth));
    }


}
