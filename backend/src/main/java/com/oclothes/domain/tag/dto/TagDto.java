package com.oclothes.domain.tag.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public abstract class TagDto {
    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long id;
        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    public static class AllResponse {
        private final List<Response> seasonTags;
        private final List<Response> moodTags;
        private final List<Response> eventTags;
    }
}
