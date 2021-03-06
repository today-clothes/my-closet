package com.oclothes.domain.clothes.service;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.global.dto.SliceDto;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

import static com.oclothes.domain.clothes.dto.ClothesDto.*;

public interface ClothesService {
    long getSizeByCloset(Closet closet);

    ClothesUploadResponse save(ClothesUploadRequest request);

    SliceDto<SearchResponse> search(SearchRequest request, Pageable pageable);

    DefaultResponse changeLockStatus(Long id);

    ClothesDetailResponse getClothesDetails(Long id);

    byte[] getImage(String url);

    void deleteById(Long id);

    void deleteAllByIdIn(Collection<Long> ids);

    String getClosetThumbnail(Closet closet);
}
