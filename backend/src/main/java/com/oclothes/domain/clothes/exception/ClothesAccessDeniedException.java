package com.oclothes.domain.clothes.exception;

import com.oclothes.global.error.exception.AuthorityException;

public class ClothesAccessDeniedException extends AuthorityException {
    public ClothesAccessDeniedException() {
        super(ClothesExceptionMessage.CLOTHES_ACCESS_DENIED.getMessage());
    }
}
