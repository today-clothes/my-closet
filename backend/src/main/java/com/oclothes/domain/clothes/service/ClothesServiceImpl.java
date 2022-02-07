package com.oclothes.domain.clothes.service;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.domain.ClothesEventTag;
import com.oclothes.domain.clothes.domain.ClothesMoodTag;
import com.oclothes.domain.clothes.domain.ClothesSeasonTag;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.clothes.exception.ClothesNotFoundException;
import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.dto.TagDto;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.global.dto.SliceDto;
import com.oclothes.infra.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.oclothes.domain.clothes.dto.ClothesDto.*;

@Transactional
@RequiredArgsConstructor
@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;
    private final ClothesMapper clothesMapper;
    private final SeasonTagRepository seasonTagRepository;
    private final EventTagRepository eventTagRepository;
    private final MoodTagRepository moodTagRepository;
    private final FileService fileService;

    @Override
    public long getSizeByCloset(Closet closet) {
        return this.clothesRepository.countByCloset(closet);
    }

    @Override
    public ClothesUploadResponse save(ClothesUploadRequest request) {
        final Clothes clothes = this.clothesRepository.save(this.clothesMapper.toEntity(request));
        final List<ClothesSeasonTag> clothesSeasonTags = this.seasonTagRepository.findAllById(request.getSeasonIds()).stream()
                .map(t -> new ClothesSeasonTag(clothes, t)).collect(Collectors.toList());
        final List<ClothesEventTag> eventTags = this.eventTagRepository.findAllById(request.getEventIds()).stream()
                .map(t -> new ClothesEventTag(clothes, t)).collect(Collectors.toList());
        final List<ClothesMoodTag> moodTags = this.moodTagRepository.findAllById(request.getMoodIds()).stream()
                .map(t -> new ClothesMoodTag(clothes, t)).collect(Collectors.toList());
        clothes.getSeasonTags().addAll(clothesSeasonTags);
        clothes.getEventTags().addAll(eventTags);
        clothes.getMoodTags().addAll(moodTags);
        return this.clothesMapper.toUploadResponse(clothes);
    }

    @Override
    public byte[] getImage(String url) {
        return this.fileService.getImage(url);
    }

    @Override
    public SliceDto<SearchResponse> searchByTag(SearchRequest request, Pageable pageable) {
        return SliceDto.create(this.clothesRepository.searchByTag(request, pageable).map(this::createSearchDtoResponse));
    }

    @Override
    public SliceDto<SearchResponse> searchByKeyword(String keyword, Pageable pageable) {
        return SliceDto.create(this.clothesRepository.findByContentContaining(keyword, pageable).map(this::createSearchDtoResponse));
    }

    @Override
    public DefaultResponse changeLockStatus(Long clothesId) {
        Clothes clothes = this.clothesRepository.findByIdAndUser(clothesId, SecurityUtils.getLoggedInUser()).orElseThrow(ClothesNotFoundException::new);
        return clothesMapper.toDefaultResponse(clothes.changeLockStatus());
    }

    private SearchResponse createSearchDtoResponse(Clothes c) {
        return new SearchResponse(
                c.getCloset().getId(), c.getId(), c.isLocked(),
                c.getSeasonTags().stream().map(t -> new TagDto.Response(t.getTag().getId(), t.getTag().getName())).collect(Collectors.toSet()),
                c.getEventTags().stream().map(t -> new TagDto.Response(t.getTag().getId(), t.getTag().getName())).collect(Collectors.toSet()),
                c.getMoodTags().stream().map(t -> new TagDto.Response(t.getTag().getId(), t.getTag().getName())).collect(Collectors.toSet()),
                c.getImgUrl());
    }
}
