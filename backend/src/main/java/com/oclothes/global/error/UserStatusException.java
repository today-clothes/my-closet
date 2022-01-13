package com.oclothes.global.error;

public class UserStatusException extends IllegalArgumentException {
    public UserStatusException(String message) {
        super(message);
    }
}
