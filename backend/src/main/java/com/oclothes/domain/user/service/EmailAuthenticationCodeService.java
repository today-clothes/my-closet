package com.oclothes.domain.user.service;

import com.oclothes.domain.user.domain.EmailAuthenticationCode;

import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;

public interface EmailAuthenticationCodeService {
    EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode);
    SignUpResponse emailAuthentication(String email, String code);
}
