package com.oclothes.domain.tag.exception;

import com.oclothes.global.error.exception.NotFoundException;

public class TagNotFoundException extends NotFoundException {
    public TagNotFoundException() {
        super(TagExceptionMessage.NOT_FOUND.getMessage());
    }
}
