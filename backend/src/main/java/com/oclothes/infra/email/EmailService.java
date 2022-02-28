package com.oclothes.infra.email;

import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.EmailSubject;

public interface EmailService {
    void sendEmail(Email email, EmailSubject subject, String message);
}
