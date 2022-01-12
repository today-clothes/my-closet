package com.oclothes.infra.email.dao;

import com.oclothes.infra.email.domain.EmailAuthenticationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthenticationCodeRepository extends JpaRepository<EmailAuthenticationCode, Long> {
}
