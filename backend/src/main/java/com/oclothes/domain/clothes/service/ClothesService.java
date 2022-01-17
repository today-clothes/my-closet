package com.oclothes.domain.clothes.service;

import com.oclothes.domain.closet.domain.Closet;

import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadRequest;
import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadResponse;

public interface ClothesService {
    long getSizeByCloset(Closet closet);

    ClothesUploadResponse save(ClothesUploadRequest request);
}
