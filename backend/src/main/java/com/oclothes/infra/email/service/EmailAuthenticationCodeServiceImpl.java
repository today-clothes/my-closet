package com.oclothes.infra.email.service;

import com.oclothes.infra.email.dao.EmailAuthenticationCodeRepository;
import com.oclothes.infra.email.domain.EmailAuthenticationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailAuthenticationCodeServiceImpl implements EmailAuthenticationCodeService {
    private final EmailAuthenticationCodeRepository emailAuthenticationCodeRepository;

    @Override
    public EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode) {
        return this.emailAuthenticationCodeRepository.save(emailAuthenticationCode);
    }
}
