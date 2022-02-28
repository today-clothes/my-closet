package com.oclothes.global.config.security.util;

import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.exception.RequiredLoggedInException;
import com.oclothes.global.config.security.service.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityUtils {

    public static User getLoggedInUser() {
        try {
            return ((CustomUserDetails) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getUser();
        } catch (NullPointerException e) {
            throw new RequiredLoggedInException();
        }
    }

}
