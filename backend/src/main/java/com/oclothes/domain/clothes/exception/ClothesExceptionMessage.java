package com.oclothes.domain.clothes.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClothesExceptionMessage {
    NOT_FOUND("해당 옷이 존재하지 않습니다.");

    private final String message;
}
