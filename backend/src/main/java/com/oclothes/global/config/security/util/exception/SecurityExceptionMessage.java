package com.oclothes.global.config.security.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessage {
    REQUIRED_LOGGED_IN("해당 서비스는 로그인이 필요한 서비스입니다.");

    private final String message;
}
