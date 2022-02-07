package com.oclothes.domain.closet.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClosetResponseMessage {
    CREATE_SUCCESS("해당 옷장 생성을 완료했습니다."),
    GET_LIST_SUCCESS("옷장 목록 가져오기를 완료했습니다."),
    CHANGE_NAME_SUCCESS("해당 옷장 이름을 변경 완료했습니다."),
    DELETE_SUCCESS("해당 옷장을 삭제 완료했습니다.");

    private final String message;
}
