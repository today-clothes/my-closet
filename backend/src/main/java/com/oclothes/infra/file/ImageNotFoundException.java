package com.oclothes.infra.file;

public class ImageNotFoundException extends IllegalArgumentException {
    private static final String MESSAGE = "해당 이미지가 존재하지 않습니다.";
    public ImageNotFoundException() {
        super(MESSAGE);
    }
}
