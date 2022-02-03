package com.oclothes.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponseMessage {
    SIGN_UP_SUCCESS("회원가입 성공."),
    LOGIN_SUCCESS("로그인 성공."),
    UPDATE_USER_PROFILE_SUCCESS("해당 회원의 프로필 업데이트를 완료하였습니다.");

    private final String message;
}
