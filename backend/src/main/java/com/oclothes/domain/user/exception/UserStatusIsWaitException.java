package com.oclothes.domain.user.exception;

import com.oclothes.global.error.exception.UserStatusException;

public class UserStatusIsWaitException extends UserStatusException {
    public UserStatusIsWaitException() {
        super(UserExceptionMessage.USER_STATUS_IS_WAIT.getMessage());
    }
}
