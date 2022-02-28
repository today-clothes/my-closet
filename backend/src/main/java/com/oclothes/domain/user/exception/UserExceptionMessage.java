package com.oclothes.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessage {
    ALREADY_EXISTS_EMAIL("해당 이메일은 이미 회원가입된 이메일입니다."),
    ALREADY_EXISTS_NICKNAME("해당 닉네임은 이미 사용 중입니다."),
    EMAIL_AUTHENTICATION_CODE_TOO_MANY_REQUEST("이메일 인증 요청 3분 후 재시도해주세요."),
    USER_STATUS_IS_WAIT("해당 이메일로 인증 번호가 전송된 상태입니다. 이메일 인증을 완료해주세요."),
    USER_NOT_FOUND("이메일 또는 패스워드를 다시 확인해주세요."),
    USER_STATUS_IS_ALREADY_NORMAL("해당 이메일은 이미 인증이 완료 되었습니다."),
    EMAIL_AUTHENTICATION_CODE_NOT_FOUND("이메일 인증 요청을 해주세요."),
    WRONG_EMAIL_AUTHENTICATION_CODE("인증번호가 틀립니다."),
    USER_STATUS_IS_WITHDRAW("탈퇴된 계정입니다.");

    private final String message;
}
