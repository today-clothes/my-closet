package com.oclothes.global.error.exception;

public class NotEmptyException extends IllegalArgumentException {
    public NotEmptyException(String s) {
        super(s);
    }
}
