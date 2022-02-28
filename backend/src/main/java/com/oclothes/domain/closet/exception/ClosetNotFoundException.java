package com.oclothes.domain.closet.exception;

import com.oclothes.global.error.exception.NotFoundException;

public class ClosetNotFoundException extends NotFoundException {
    public ClosetNotFoundException() {
        super(ClosetExceptionMessage.CLOSET_NOT_FOUND.getMessage());
    }

    public ClosetNotFoundException(String message) {
        super(message);
    }
}
