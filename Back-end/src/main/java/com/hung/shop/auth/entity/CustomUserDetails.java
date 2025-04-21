package com.hung.shop.auth.entity;

import com.hung.shop.share.UserAuthPOJO;
import com.hung.shop.share.UsersPOJO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private UsersPOJO user;
    private Set<GrantedAuthority> authoritySet;

    public CustomUserDetails(UsersPOJO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // If you want to support roles later, return them here
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return user.getUserAuthPOJO().stream()
                .filter(auth -> auth.getPassword() != null)
                .map(UserAuthPOJO::getPassword)
                .findFirst()
                .orElse(null);
    }
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }

    public UsersPOJO getUser() {
        return user;
    }
}