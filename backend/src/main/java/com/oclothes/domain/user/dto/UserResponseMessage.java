package com.oclothes.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponseMessage {
    SIGN_UP("회원가입 성공."),
    LOGIN("로그인 성공.");

    private final String message;
}
