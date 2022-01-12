package com.oclothes.infra.email.service;

import com.oclothes.infra.email.domain.EmailAuthenticationCode;

public interface EmailAuthenticationCodeService {
    EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode);
}
