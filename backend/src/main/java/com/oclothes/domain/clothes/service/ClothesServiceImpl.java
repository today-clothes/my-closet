package com.oclothes.domain.clothes.service;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.*;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.clothes.exception.ClothesNotFoundException;
import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.dto.TagDto;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.global.dto.SliceDto;
import com.oclothes.infra.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.oclothes.domain.clothes.dto.ClothesDto.*;
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
    public SliceDto<SearchResponse> search(SearchRequest request, Pageable pageable) {
        return SliceDto.create(this.clothesRepository
                .searchByTag(request, getUser(request), pageable)
                .map(this::createSearchDtoResponse));
    }

    @Override
    public DefaultResponse changeLockStatus(Long id) {
        Clothes clothes = this.clothesRepository.findByIdAndUser(id, SecurityUtils.getLoggedInUser()).orElseThrow(ClothesNotFoundException::new);
        return this.clothesMapper.toDefaultResponse(clothes.changeLockStatus());
    }

    @Override
    public ClothesResponse getClothesDetails(Long id) {
        Clothes clothes = this.clothesRepository.findClothesDetails(id).orElseThrow(ClothesNotFoundException::new);
        return createClothesResponse(clothes, clothes.getUser());
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

    public Clothes findById(Long id) {
        return this.clothesRepository.findById(id).orElseThrow(ClothesNotFoundException::new);
    }

    private User getUser(SearchRequest req){
        return isNull(req) || isNull(req.getKeyword()) ? null : SecurityUtils.getLoggedInUser();
    }

    private SearchResponse createSearchDtoResponse(Clothes c) {
        return new SearchResponse(
                c.getCloset().getId(),
                c.getId(),
                c.isLocked(),
                mapSeasonTags(c.getSeasonTags()),
                mapEventTags(c.getEventTags()),
                mapMoodTags(c.getMoodTags()),
                c.getImgUrl());
    }

    private ClothesResponse createClothesResponse(Clothes c, User user){
        ClothesResponse response = ClothesResponse.builder()
                .styleTitle(c.getStyleTitle())
                .content(c.getContent())
                .updateAt(c.getUpdatedAt())
                .seasonTags(mapSeasonTags(c.getSeasonTags()))
                .moodTags(mapMoodTags(c.getMoodTags()))
                .eventTags(mapEventTags(c.getEventTags())).build();

        if(!SecurityUtils.getLoggedInUser().getId().equals(user.getId())){
            response.setUserInfo(user);
        }
        return response;
    }

    private Set<TagDto.Response> mapSeasonTags(Set<ClothesSeasonTag> tag) {
        return tag.stream().map(t -> new TagDto.Response(t.getTag().getId(), t.getTag().getName())).collect(Collectors.toSet());
    }

    private Set<TagDto.Response> mapMoodTags(Set<ClothesMoodTag> tag) {
        return tag.stream().map(t -> new TagDto.Response(t.getTag().getId(), t.getTag().getName())).collect(Collectors.toSet());
    }

    private Set<TagDto.Response> mapEventTags(Set<ClothesEventTag> tag) {
        return tag.stream().map(t -> new TagDto.Response(t.getTag().getId(), t.getTag().getName())).collect(Collectors.toSet());
    }
}
