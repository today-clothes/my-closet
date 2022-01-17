package com.oclothes.infra.email;

import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.EmailSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(Email email, EmailSubject subject, String message) {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(email.getValue());
            messageHelper.setSubject(subject.getSubject());
            messageHelper.setText(message, true);
        } catch (MessagingException e) {
        }
        this.javaMailSender.send(mimeMessage);
    }

}
