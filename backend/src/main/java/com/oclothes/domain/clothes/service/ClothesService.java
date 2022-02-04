package com.oclothes.domain.clothes.service;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.global.dto.SliceDto;
import org.springframework.data.domain.Pageable;

import static com.oclothes.domain.clothes.dto.ClothesDto.*;
import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadRequest;
import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadResponse;

public interface ClothesService {
    long getSizeByCloset(Closet closet);

    ClothesUploadResponse save(ClothesUploadRequest request);

    SliceDto<SearchResponse> searchByTag(SearchRequest request, Pageable pageable);

    SliceDto<SearchResponse> searchAllClosetByTag(SearchRequest request, Pageable pageable);

    SliceDto<SearchResponse> searchByKeyword(SearchKeywordRequest request, Pageable pageable);

    byte[] getImage(String url);
}
