package com.oclothes.domain.user.dto;

import com.oclothes.domain.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class UserDto {
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

    @Getter
    @RequiredArgsConstructor
    public static class ProfileUpdateRequest{
        @NotNull(message = "회원 id를 입력해주세요.")
        private final Long id;
        @NotNull(message = "성별을 선택해주세요.")
        private final User.Gender gender;
        @Min(value = 0, message = "나이는 0보다 작을 수 없습니다.")
        @NotNull(message = "나이를 입력해주세요.")
        private final Integer age;
        @Min(value = 0, message = "키는 0보다 작을 수 없습니다.")
        @NotNull(message = "키를 입력해주세요.")
        private final Integer height;
        @Min(value = 0, message = "몸무게는 0보다 작을 수 없습니다.")
        @NotNull(message = "몸무게를 입력해주세요.")
        private final Integer weight;
    }

    @Getter
    @RequiredArgsConstructor
    public static class DefaultResponse {
        private final Long id;
        private final User.Gender gender;
        private final Integer age;
        private final Integer height;
        private final Integer weight;
    }
}
