package com.oclothes.infra.email.service;

import com.oclothes.domain.user.domain.Email;
import com.oclothes.infra.email.domain.EmailSubject;

public interface EmailService {
    void sendEmail(Email email, EmailSubject subject, String message);
}
