package com.oclothes.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessage {

    ALREADY_EXISTS_EMAIL("해당 이메일은 이미 회원가입된 이메일입니다."),
    EMAIL_AUTHENTICATION_CODE_TOO_MANY_REQUEST("이메일 인증 요청 3분 후 재시도해주세요."),
    USER_STATUS_IS_WAIT("해당 이메로 인증 번호가 전송된 상태입니다. 이메일 인증을 완료해주세요.");

    private final String message;
}
