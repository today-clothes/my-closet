package com.oclothes.infra.email.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class EmailMessageUtil {
    public static String getSignUpEmailMessage(String authenticationCode) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new ClassPathResource("mail/sign-up.html").getFile())));
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) stringBuilder.append(bufferedReader.readLine());
            return stringBuilder.toString().replace("{authCode}", authenticationCode);
        } catch (IOException e) {
            throw new IllegalArgumentException("회원가입 인증 메일 가져오는 도중 엑셉션 발생");
        }
    }
}
