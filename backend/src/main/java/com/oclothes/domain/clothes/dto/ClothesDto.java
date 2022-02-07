package com.oclothes.domain.clothes.dto;

import com.oclothes.domain.tag.dto.TagDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public abstract class ClothesDto {
    @Getter
    @RequiredArgsConstructor
    public static class ClothesUploadRequest {
        @NotNull(message = "옷장(closet) ID를 입력해주세요.")
        private final Long closetId;
        private final List<Long> seasonIds;
        private final List<Long> eventIds;
        private final List<Long> moodIds;
        @NotNull(message = "저장할 이미지는 null일 수 없습니다.")
        private final MultipartFile file;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ClothesUploadResponse {
        private final Long closetId;
        private final Long clothesId;
        private final String imgUrl;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SearchRequest {
        private final Long closetId;
        private final List<Long> seasonTagIds;
        private final List<Long> eventTagIds;
        private final List<Long> moodTagIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SearchResponse {
        private final Long closetId;
        private final Long clothesId;
        private final boolean locked;
        private final Set<TagDto.Response> seasonTags;
        private final Set<TagDto.Response> eventTags;
        private final Set<TagDto.Response> moodTags;
        private final String imgUrl;
    }

    @Getter
    @RequiredArgsConstructor
    public static class DefaultResponse {
        private final Long clothesId;
        private final boolean locked;
    }
}
