package com.oclothes.global.config.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessage {
    REQUIRED_LOGGED_IN("해당 서비스는 로그인이 필요한 서비스입니다."),
    ACCESS_DENIED("잘못된 접근입니다."),
    NO_AUTHENTICATION("다시 로그인 해주세요.");

    private final String message;
}
