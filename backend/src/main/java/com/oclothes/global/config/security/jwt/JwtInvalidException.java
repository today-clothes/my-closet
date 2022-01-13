package com.oclothes.global.config.security.jwt;

public class JwtInvalidException extends IllegalArgumentException {
    public JwtInvalidException() {
        super(JwtProperties.JWT_INVALID_MESSAGE);
    }
}
