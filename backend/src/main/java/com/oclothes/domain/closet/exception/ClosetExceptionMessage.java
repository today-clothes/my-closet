package com.oclothes.domain.closet.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClosetExceptionMessage {
    CLOSET_NOT_FOUND("해당 옷장이 존재하지 않습니다."),
    CLOSET_NOT_EMPTY("해당 옷장이 비어있지 않습니다. 옷장 안에 있는 옷을 전부 삭제하시거나, 다른 옷장으로 이동 후 삭제 바랍니다.");

    private final String message;
}
