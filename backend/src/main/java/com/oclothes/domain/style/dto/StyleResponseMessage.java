package com.oclothes.domain.style.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StyleResponseMessage {
    FIND_ALL_SUCCESS("태그 목록 가져오기 성공.");

    private final String message;
}
