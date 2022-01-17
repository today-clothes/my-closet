package com.oclothes.domain.user.service;

import com.oclothes.domain.user.dao.EmailAuthenticationCodeRepository;
import com.oclothes.domain.user.domain.EmailAuthenticationCode;
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
