package com.oclothes.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    INVALID_REQUEST("해당 요청 값이 요구하는 형식과 일치하지 않습니다.");

    private final String message;
}
