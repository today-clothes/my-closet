package com.oclothes.domain.user.exception;

import com.oclothes.global.error.NotFoundException;

public class EmailAuthenticationCodeNotFoundException extends NotFoundException {
    public EmailAuthenticationCodeNotFoundException() {
        super(UserExceptionMessage.EMAIL_AUTHENTICATION_CODE_NOT_FOUND.getMessage());
    }

    public EmailAuthenticationCodeNotFoundException(String message) {
        super(message);
    }
}
