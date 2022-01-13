package com.oclothes.domain.user.exception;

import com.oclothes.global.error.exception.AlreadyExistsException;

public class AlreadyExistsEmailException extends AlreadyExistsException {
    public AlreadyExistsEmailException() {
        super(UserExceptionMessage.ALREADY_EXISTS_EMAIL.getMessage());
    }

    public AlreadyExistsEmailException(String message) {
        super(message);
    }
}
