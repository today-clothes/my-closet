package com.oclothes.global.config.security.service;

import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.createUserDetails(this.userService.findByEmail(email));
    }

    private UserDetails createUserDetails(User user) {
        return new CustomUserDetails(user);
    }
}
