package com.oclothes.domain.user.exception;

import com.oclothes.global.error.exception.UserStatusException;

public class AlreadyEmailAuthenticationException extends UserStatusException {
    public AlreadyEmailAuthenticationException() {
        super(UserExceptionMessage.USER_STATUS_IS_ALREADY_NORMAL.getMessage());
    }
}
