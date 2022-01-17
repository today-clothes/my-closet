package com.oclothes.domain.user.dao;

import com.oclothes.domain.user.domain.EmailAuthenticationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthenticationCodeRepository extends JpaRepository<EmailAuthenticationCode, Long> {
}
