package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ClothesSupportRepository {
    Slice<Clothes> searchByTag(ClothesDto.SearchRequest request, Pageable pageable);
}
