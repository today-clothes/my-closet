package com.oclothes.domain.clothes.exception;

import com.oclothes.global.error.exception.NotFoundException;

public class ClothesNotFoundException extends NotFoundException {
    public ClothesNotFoundException() { super(ClothesExceptionMessage.CLOTHES_NOT_FOUND.getMessage());}
}
