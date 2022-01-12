package com.oclothes.domain.user.exception;

import com.oclothes.global.error.TooManyRequestException;

public class EmailAuthenticationCodeTooManyRequestException extends TooManyRequestException {
    public EmailAuthenticationCodeTooManyRequestException() {
        super(UserExceptionMessage.EMAIL_AUTHENTICATION_CODE_TOO_MANY_REQUEST.getMessage());
    }

    public EmailAuthenticationCodeTooManyRequestException(String message) {
        super(message);
    }
}
