package com.hung.shop.services;

import com.hung.shop.entity.CustomUserDetails;
import com.hung.shop.entity.Users;
import com.hung.shop.repositories.UserAuthRepository;
import com.hung.shop.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Users user = usersRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//        return new CustomUserDetails(user);
//    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String role="";
        Set<GrantedAuthority> authoritySet =new HashSet<>();
        Users user=new Users();
        user.setEmail(email);
        return new CustomUserDetails(user);
    }
}
