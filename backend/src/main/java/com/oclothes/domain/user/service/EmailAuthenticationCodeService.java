package com.oclothes.domain.user.service;

import com.oclothes.domain.user.domain.EmailAuthenticationCode;

public interface EmailAuthenticationCodeService {
    EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode);
}
