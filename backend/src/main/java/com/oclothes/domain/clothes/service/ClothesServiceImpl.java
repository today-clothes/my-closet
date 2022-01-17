package com.oclothes.domain.clothes.service;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.style.domain.Style;
import com.oclothes.domain.style.dto.StyleMapper;
import com.oclothes.domain.style.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadRequest;
import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadResponse;

@Transactional
@RequiredArgsConstructor
@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;
    private final StyleService styleService;

    private final ClothesMapper clothesMapper;
    private final StyleMapper styleMapper;

    @Override
    public long getSizeByCloset(Closet closet) {
        return this.clothesRepository.countByCloset(closet);
    }

    @Override
    public ClothesUploadResponse save(ClothesUploadRequest request) {
        final List<Style> styles = this.styleService.findAllById(request.getStyleIds());
        final Clothes clothes = this.clothesRepository.save(this.clothesMapper.toEntity(request))
                .addAllClothStyle(this.styleMapper.toClothesStyle(styles));
        return this.clothesMapper.toUploadResponse(clothes);
    }
}
