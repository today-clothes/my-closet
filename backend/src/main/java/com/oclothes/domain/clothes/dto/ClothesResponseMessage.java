package com.oclothes.domain.clothes.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClothesResponseMessage {
    UPLOAD_SUCCESS("해당 옷장에 옷을 저장했습니다.");

    private final String message;
}