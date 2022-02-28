package com.oclothes.domain.tag.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagResponseMessage {
    ALL_RESPONSE_SUCCESS("태그 전체 리스트 반환 성공");

    private final String message;
}
