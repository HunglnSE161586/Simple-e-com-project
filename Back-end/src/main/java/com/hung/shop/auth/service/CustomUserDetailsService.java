package com.hung.shop.auth.service;

import com.hung.shop.auth.entity.CustomUserDetails;
import com.hung.shop.entity.Users;
import com.hung.shop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String role="";
        Set<GrantedAuthority> authoritySet =new HashSet<>();
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        if (user.getRole() != null) {
            role = user.getRole().getRoleName();
            authoritySet.add(new SimpleGrantedAuthority(role));
        }

        return new CustomUserDetails(user,authoritySet);
    }
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        String role="";
//        Set<GrantedAuthority> authoritySet =new HashSet<>();
//        Users user=new Users();
//        user.setEmail(email);
//        return new CustomUserDetails(user);
//    }
}
