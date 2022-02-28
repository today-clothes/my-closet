package com.oclothes.domain.user.exception;

public class WrongEmailAuthenticationCodeException extends IllegalArgumentException {
    public WrongEmailAuthenticationCodeException() {
        super(UserExceptionMessage.WRONG_EMAIL_AUTHENTICATION_CODE.getMessage());
    }

    public WrongEmailAuthenticationCodeException(String s) {
        super(s);
    }
}
