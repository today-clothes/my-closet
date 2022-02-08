package com.oclothes.domain.closet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class ClosetDto {
    @Getter
    @RequiredArgsConstructor
    public static class DefaultResponse {
        private final Long id;
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "옷장 이름을 입력해주세요.")
        private String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NameUpdateRequest {
        @NotNull(message = "옷장 id를 입력해주세요.")
        private Long id;
        @NotBlank(message = "옷장 이름은 공백일 수 없습니다.")
        private String name;
    }
}
