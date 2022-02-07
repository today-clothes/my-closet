package com.oclothes.domain.clothes.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClothesResponseMessage {
    UPLOAD_SUCCESS("해당 옷장에 옷을 저장했습니다."),
    TAG_FILTER_SUCCESS("해당 옷장에 필터링된 옷을 반환했습니다."),
    KEYWORD_SEARCH_SUCCESS("해당 검색어랑 일치하는 옷을 반환했습니다."),
    CHANGE_LOCKED_STATUS_SUCCESS("해당 옷 공개 상태를 변경 완료했습니다.");
    private final String message;
}
