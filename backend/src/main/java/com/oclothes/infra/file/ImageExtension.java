package com.oclothes.infra.file;

import com.oclothes.global.error.exception.FileExtensionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum ImageExtension {
    JPG("jpg"), JPEG("jpeg"), PNG("png"), HEIC("heic");

    private final String value;

    public static void validateImageExtension(String extension) {
        try {
            ImageExtension.valueOf(extension.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new FileExtensionException("이미지 파일이 아닙니다.");
        }
    }
}
