package com.oclothes.infra.email.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailSubject {
    SIGN_UP("[오늘의 옷] 회원가입 인증 메일");

    private final String subject;
}
