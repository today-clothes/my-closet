package com.oclothes.domain.clothes.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class ClothesDto {
    @Getter
    @RequiredArgsConstructor
    public static class ClothesUploadRequest {
        @NotNull(message = "closet id를 입력해주세요.")
        private final Long closetId;
        @NotNull(message = "최소 하나의 태그를 선택해주세요.")
        private final List<Long> styleIds;
        private final String location;
        @NotNull(message = "저장할 이미지는 null일 수 없습니다.")
        private final MultipartFile file;
    }
    @Getter
    @RequiredArgsConstructor
    public static class ClothesUploadResponse {
        private final Long closetId;
        private final Long clothesId;
    }
}
