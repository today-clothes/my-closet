package com.oclothes.domain.user.dto;

import com.oclothes.domain.user.domain.UserPersonalInformation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.*;
import java.util.Set;

public abstract class UserDto {
    @Getter
    @RequiredArgsConstructor
    public static class SignUpRequest {
        @Email(message = "이메일 형식이 일치하지 않습니다.")
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        private final String email;
        @Length(min = 6, max = 20, message = "패스워드는 최소 6자 이상 20자 이하여야 합니다.")
        private final String password;
        @Size(min = 1, max = 20, message = "닉네임은 1자 이상 20자 이하로 입력해주세요.")
        @NotNull(message = "닉네임을 입력해주세요.")
        private final String nickname;
        @NotNull(message = "성별을 기입해주세요. (MALE, FEMALE)")
        private final UserPersonalInformation.Gender gender;
        @Min(value = 0, message = "나이를 다시 입력해주세요.")
        @Max(value = 150, message = "나이를 다시 입력해주세요.")
        @NotNull(message = "나이를 입력해주세요.")
        private final Integer age;
        @Min(value = 50, message = "키를 다시 입력해주세요. (50이상 300이하)")
        @Max(value = 300, message = "키를 다시 입력해주세요. (50이상 300이하)")
        private final Integer height;
        @Min(value = 0, message = "몸무게는 0보다 작을 수 없습니다.")
        @NotNull(message = "몸무게를 입력해주세요.")
        private final Integer weight;
        @Size(min = 2, message = "최소 두 개 이상의 무드 태그를 선택해주세요.")
        @NotNull(message = "최소 두 개 이상의 무드 태그를 선택해주세요.")
        private final Set<Long> moodTags;
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
    public static class ProfileUpdateRequest {
        @NotNull(message = "회원 id를 입력해주세요.")
        private final Long id;
        @Size(min = 1, max = 20, message = "닉네임은 1자 이상 20자 이하로 입력해주세요.")
        @NotNull(message = "닉네임을 입력해주세요.")
        private final String nickname;
        @NotNull(message = "성별을 선택해주세요.")
        private final UserPersonalInformation.Gender gender;
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
        private final String nickname;
        private final UserPersonalInformation.Gender gender;
        private final Integer age;
        private final Integer height;
        private final Integer weight;
    }
}
