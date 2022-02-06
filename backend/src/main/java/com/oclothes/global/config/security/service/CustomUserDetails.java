package com.oclothes.global.config.security.service;

import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.exception.UserStatusIsWaitException;
import com.oclothes.domain.user.exception.UserStatusIsWithdrawException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail().getValue();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
        switch (this.user.getStatus()) {
            case NORMAL: return true;
            case WAIT: throw new UserStatusIsWaitException();
            case WITHDRAW: throw new UserStatusIsWithdrawException();
            default: return false;
        }
    }

}
