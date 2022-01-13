package com.oclothes.domain.user.exception;

import com.oclothes.global.error.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super(UserExceptionMessage.USER_NOT_FOUND.getMessage());
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
