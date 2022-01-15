package com.oclothes.domain.closet.exception;

import com.oclothes.global.error.exception.NotEmptyException;

public class ClosetNotEmptyException extends NotEmptyException {
    public ClosetNotEmptyException() {
        super(ClosetExceptionMessage.CLOSET_NOT_EMPTY.getMessage());
    }

    public ClosetNotEmptyException(String s) {
        super(s);
    }
}
