package com.hung.shop.auth.service.impl;

import com.hung.shop.auth.entity.CustomUserDetails;
import com.hung.shop.auth.service.IUserAuthService;
import com.hung.shop.userRole.service.IUserRoleService;
import com.hung.shop.share.UserAuthPOJO;
import com.hung.shop.share.UsersPOJO;
import com.hung.shop.user.service.IUserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserService userService;
    private final IUserAuthService userAuthService;
    private final IUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            String role="";
            Set<GrantedAuthority> authoritySet =new HashSet<>();
            UsersPOJO user = userService.getUserByEmail(email);
            List<UserAuthPOJO> userAuthPOJOS=userAuthService.getUserAuthsByUserId(user.getId());
            user.setUserAuthPOJO(userAuthPOJOS);
            user.setUserRolePOJO(userRoleService.findById(user.getUserRolePOJO().getId()).orElse(null));
            if (user.getUserRolePOJO().getRoleName() != null) {
                role = user.getUserRolePOJO().getRoleName();
                authoritySet.add(new SimpleGrantedAuthority(role));
            }

            return new CustomUserDetails(user,authoritySet);
        }catch (Exception e){
            throw new UsernameNotFoundException("User not found with email: " + email, e);
        }
    }
}
