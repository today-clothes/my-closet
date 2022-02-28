package com.oclothes.domain.user.dao;

import com.oclothes.domain.user.domain.EmailAuthenticationCode;
import com.oclothes.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthenticationCodeRepository extends JpaRepository<EmailAuthenticationCode, Long> {
    Optional<EmailAuthenticationCode> findByUser(User user);
}
