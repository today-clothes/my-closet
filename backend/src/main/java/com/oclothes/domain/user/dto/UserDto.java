package com.oclothes.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {

    @Getter
    @RequiredArgsConstructor
    public static class SignUpRequest {
        @Email(message = "이메일 형식이 일치하지 않습니다.")
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        private final String email;
        @Length(min = 6, max = 20, message = "패스워드는 최소 6자 이상 20자 이하여야 합니다.")
        private final String password;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SignUpResponse {
        private final String email;
    }

    @Getter
    @RequiredArgsConstructor
    public static class LoginRequest {
        @Email(message = "이메일 형식이 일치하지 않습니다.")
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        private final String email;

        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        private final String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class LoginResponse {
        private final String accessToken;
    }

}
