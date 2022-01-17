package com.oclothes.domain.style.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class StyleDto {
    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long id;
        private final String name;
    }
}
