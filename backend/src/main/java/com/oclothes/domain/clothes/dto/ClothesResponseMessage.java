package com.oclothes.domain.clothes.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClothesResponseMessage {
    UPLOAD_SUCCESS("해당 옷장에 옷을 저장했습니다."),
    CLOTHES_DETAILS_SUCCESS("옷 상세정보를 반환했습니다."),
    SEARCH_CLOTHES_SUCCESS("옷 검색을 완료했습니다."),
    CHANGE_LOCKED_STATUS_SUCCESS("해당 옷 공개 상태를 변경 완료했습니다."),
    DELETE_SUCCESS("해당 옷 삭제를 완료했습니다."),
    DELETE_All_SUCCESS("해당 옷들을 삭제 완료했습니다."),
    RECOMMEND_CLOTHES_SUCCESS("옷 추천 리스트를 반환 성공했습니다.");

    private final String message;
}
