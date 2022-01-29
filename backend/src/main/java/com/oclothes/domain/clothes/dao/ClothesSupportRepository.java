package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesDto;

import java.util.List;

public interface ClothesSupportRepository {
    List<Clothes> searchByTag(ClothesDto.SearchRequest request);
}
