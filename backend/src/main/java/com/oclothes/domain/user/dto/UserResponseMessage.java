package com.oclothes.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponseMessage {
    SIGN_UP_SUCCESS("회원가입 성공."),
    LOGIN_SUCCESS("로그인 성공."),
    UPDATE_USER_ACCOUNT_SUCCESS("해당 회원의 계정 정보 업데이트를 완료했습니다."),
    UPDATE_USER_PROFILE_SUCCESS("해당 회원의 프로필 업데이트를 완료했습니다."),
    GET_USER_SUCCESS("회원 정보 가져오기를 완료했습니다.");

    private final String message;
}
