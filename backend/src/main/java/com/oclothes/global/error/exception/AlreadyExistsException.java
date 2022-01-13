package com.oclothes.global.error.exception;

public class AlreadyExistsException extends IllegalArgumentException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
