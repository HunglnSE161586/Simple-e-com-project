package com.hung.shop.auth.service.impl;

import com.hung.shop.auth.entity.CustomUserDetails;
import com.hung.shop.auth.service.IUserAuthService;
import com.hung.shop.services.IUserRoleService;
import com.hung.shop.share.UserAuthPOJO;
import com.hung.shop.share.UsersPOJO;
import com.hung.shop.user.internal.IUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserAuthService userAuthService;
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String role="";
        Set<GrantedAuthority> authoritySet =new HashSet<>();
        UsersPOJO user = userService.getUserByEmail(email);
        List<UserAuthPOJO> userAuthPOJOS=userAuthService.getUserAuthsByUserId(user.getUserId());
        user.setUserAuthPOJO(userAuthPOJOS);
        user.setUserRolePOJO(userRoleService.findById(user.getUserRolePOJO().getRoleId()).orElse(null));
        if (user.getUserRolePOJO().getRoleName() != null) {
            role = user.getUserRolePOJO().getRoleName();
            authoritySet.add(new SimpleGrantedAuthority(role));
        }

        return new CustomUserDetails(user,authoritySet);
    }
}
