package com.oclothes.domain.clothes.service;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.clothes.exception.ClothesNotFoundException;
import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.dto.SliceDto;
import com.oclothes.infra.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.oclothes.domain.clothes.dto.ClothesDto.*;
import static com.oclothes.global.config.security.util.SecurityUtils.getLoggedInUser;
import static java.util.Objects.isNull;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
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
        return this.clothesMapper.toUploadResponse(
                this.clothesRepository.save(this.clothesMapper.toEntity(request))
                        .addAllSeasonTags(this.seasonTagRepository.findAllById(request.getSeasonIds()))
                        .addAllEventTags(this.eventTagRepository.findAllById(request.getEventIds()))
                        .addAllMoodTags(this.moodTagRepository.findAllById(request.getMoodIds())));
    }

    @Override
    public SliceDto<SearchResponse> search(SearchRequest request, Pageable pageable) {
        return SliceDto.create(this.clothesRepository
                .searchByTag(request, this.existsKeywordThenReturnUserOrElseNull(request), pageable)
                .map(this.clothesMapper::toSearchResponse));
    }

    @Override
    public DefaultResponse changeLockStatus(Long id) {
        return this.clothesMapper.toDefaultResponse(
                this.clothesRepository.findByIdAndUser(id, getLoggedInUser())
                        .orElseThrow(ClothesNotFoundException::new)
                        .changeLockStatus());
    }

    @Override
    public ClothesDetailResponse getClothesDetails(Long id) {
        return this.clothesMapper.toClothesDetailResponse(
                this.clothesRepository.findClothesDetails(id)
                        .orElseThrow(ClothesNotFoundException::new)
                        .validateAuthority(getLoggedInUser()));
    }

    @Override
    public byte[] getImage(String url) {
        return this.fileService.getImage(url);
    }

    @Override
    public void deleteById(Long id) {
        this.fileService.delete(this.findById(id).getImgUrl());
        this.clothesRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIdIn(Collection<Long> ids) {
        final List<Clothes> clothes = this.clothesRepository.findAllByIdIn(ids);
        if (clothes.isEmpty()) return;
        this.fileService.deleteAll(clothes.stream().map(Clothes::getImgUrl).collect(Collectors.toList()));
        this.clothesRepository.deleteAllByIdIn(ids);
    }

    @Override
    public String getClosetThumbnail(Closet closet) {
        Clothes clothes = this.clothesRepository.findFirstByClosetOrderByCreatedAtDesc(closet).orElse(null);
        if (isNull(clothes)) return null;
        return clothes.getImgUrl();
    }

    public Clothes findById(Long id) {
        return this.clothesRepository.findById(id).orElseThrow(ClothesNotFoundException::new);
    }

    private User existsKeywordThenReturnUserOrElseNull(SearchRequest req) {
        return isNull(req) || isNull(req.getKeyword()) ? getLoggedInUser() : null;
    }
}
