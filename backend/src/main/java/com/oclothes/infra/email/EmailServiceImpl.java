package com.oclothes.infra.email;

import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.EmailSubject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
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
            log.info(e.getMessage());
        }
        this.javaMailSender.send(mimeMessage);
    }

}
