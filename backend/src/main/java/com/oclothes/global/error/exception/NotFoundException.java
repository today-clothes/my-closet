package com.oclothes.global.error.exception;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(String message) {
        super(message);
    }
}
