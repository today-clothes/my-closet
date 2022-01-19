package com.oclothes.domain.tag.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class TagDto {
    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long id;
        private final String name;
    }
}
