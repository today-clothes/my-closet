package com.oclothes.domain.clothes.service;

import com.oclothes.domain.clothes.dto.ClothesDto;

import java.util.List;

public interface ClothesRecommendService {
   List<ClothesDto.SearchResponse> recommendClothes();
}
