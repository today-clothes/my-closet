package com.oclothes.domain.tag.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagExceptionMessage {
    NOT_FOUND("해당 태그가 존재하지 않습니다.");

    private final String message;
}
