package com.oclothes.domain.clothes.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.domain.ClothesMoodTag;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.clothes.dto.ClothesMapperSupport;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.domain.UserPersonalInformation;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.infra.file.FileService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ClothesRecommendServiceImplTest extends BaseTest {
    @Mock
    private ClothesRepository clothesRepository;

    @Mock
    private FileService fileService;

    @Mock
    private MoodTagRepository moodTagRepository;

    @Spy
    @InjectMocks
    private ClothesMapperSupport clothesMapperSupport;

    @Spy
    @InjectMocks
    private ClothesMapper clothesMapper = Mappers.getMapper(ClothesMapper.class);

    @InjectMocks
    private ClothesRecommendServiceImpl clothesRecommendService;

    private static MockedStatic<SecurityUtils> securityUtilsMock;

    @BeforeAll
    static void beforeAll() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
    }

    @AfterAll
    static void afterAll() {
        securityUtilsMock.close();
    }

    @DisplayName("유저 정보에 따라 추천 옷 목록이 반환된다.")
    @Test
    void recommendClothes() {
        // MoodTag 설정 : MoodTag List 생성 및 MoodTagId 설정
        List<MoodTag> moodTags = this.setMoodTags();

        // User 설정 : User 생성 및 MoodTag 부여 : (1, 2, 3)
        User user = User.builder().gender(UserPersonalInformation.Gender.MALE).age(10).height(100).weight(100).build();
        user.addAllMoodTags(moodTags.subList(0, 3));

        // Clothes 설정 : clothes 데이터 생성 및 MoodTag 부여
        // clothes1's MoodTag : (1)
        // clothes2's MoodTag : (1, 2, 3)
        // clothes3's MoodTag : (1, 2)
        Clothes clothes1 = this.setClothes(1L, moodTags.subList(0, 1));
        Clothes clothes2 = this.setClothes(2L, moodTags.subList(0, 3));
        Clothes clothes3 = this.setClothes(3L, moodTags.subList(0, 2));
        List<Clothes> clothesList = Arrays.asList(clothes1, clothes2, clothes3);

        this.injectClothesMapper();

        // expected 설정 : clothesId 순서 2 -> 3 -> 1
        List<ClothesDto.SearchResponse> expected = Arrays.asList(this.clothesMapper.toSearchResponse(clothesList.get(1)), this.clothesMapper.toSearchResponse(clothesList.get(2)), this.clothesMapper.toSearchResponse(clothesList.get(0)));

        // Test
        securityUtilsMock.when(SecurityUtils::getLoggedInUser).thenReturn(user);
        when(this.clothesRepository.findAllForRecommend(any(), anyInt(), anyInt(), anyInt())).thenReturn(clothesList);

        final List<ClothesDto.SearchResponse> result = this.clothesRecommendService.recommendClothes();

        for (int i = 0; i < 3; i++) {
            assertEquals(expected.get(i).getClothesId(), result.get(i).getClothesId());
        }
    }

    private void injectClothesMapper() {
        try {
            Field clothesMapper = this.clothesRecommendService.getClass().getDeclaredField("clothesMapper");
            clothesMapper.setAccessible(true);
            clothesMapper.set(this.clothesRecommendService, this.clothesMapper);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List<MoodTag> setMoodTags() {
        AtomicLong index = new AtomicLong(1);
        return Stream.of(new MoodTag("mood1"), new MoodTag("mood2"), new MoodTag("mood3"), new MoodTag("mood4"), new MoodTag("mood5"))
                .peek(tag -> {
                    try {
                        Field moodId = tag.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
                        moodId.setAccessible(true);
                        moodId.set(tag, index.getAndIncrement());
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                })
                .collect(Collectors.toList());
    }

    private Clothes setClothes(Long clothesId, List<MoodTag> moodTags) {
        Clothes clothes = Clothes.builder().build();
        Set<ClothesMoodTag> clothesMoodTag = new HashSet<>();
        moodTags.forEach(m -> clothesMoodTag.add(new ClothesMoodTag(clothes, m)));
        clothes.setMoodTags(clothesMoodTag);

        try {
            Field clothesIdField = clothes.getClass().getSuperclass().getDeclaredField("id");
            clothesIdField.setAccessible(true);
            clothesIdField.set(clothes, clothesId);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return clothes;
    }

}
