package com.oclothes.domain.style.dto;

import com.oclothes.domain.style.domain.Style;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class StyleDto {
    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long id;
        private final Style.TYPE type;
        private final String name;
    }
}
