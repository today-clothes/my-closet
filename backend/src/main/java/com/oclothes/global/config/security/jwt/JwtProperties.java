package com.oclothes.global.config.security.jwt;

public interface JwtProperties {
    String AUTHORITIES_KEY = "auth";
    String BEARER_TYPE = "Bearer ";
    String JWT_INVALID_MESSAGE = "다시 로그인 해주세요.";
}
