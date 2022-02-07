package com.oclothes.domain.user.exception;

import com.oclothes.global.error.exception.AlreadyExistsException;

public class AlreadyExistsNicknameException extends AlreadyExistsException {
    public AlreadyExistsNicknameException(){ super(UserExceptionMessage.ALREADY_EXISTS_NICKNAME.getMessage()); }

    public AlreadyExistsNicknameException(String message) { super(message); }
}
