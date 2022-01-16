package com.oclothes.global.config.security.util.exception;

public class RequiredLoggedInException extends IllegalArgumentException {
    public RequiredLoggedInException() {
        super(SecurityExceptionMessage.REQUIRED_LOGGED_IN.getMessage());
    }

    public RequiredLoggedInException(String s) {
        super(s);
    }
}
