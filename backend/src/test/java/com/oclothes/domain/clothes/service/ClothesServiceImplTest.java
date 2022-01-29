package com.oclothes.domain.clothes.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.domain.EventTag;
import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.tag.domain.SeasonTag;
import com.oclothes.domain.user.dto.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClothesServiceImplTest extends BaseTest {

    @Mock
    private ClothesRepository clothesRepository;

    @Mock
    private ClosetRepository closetRepository;

    @Mock
    private SeasonTagRepository seasonTagRepository;

    @Mock
    private MoodTagRepository moodTagRepository;

    @Mock
    private EventTagRepository eventTagRepository;

    //@Spy
    //private ClothesMapper clothesMapper;

    @Spy
    @InjectMocks
    private final ClothesMapper clothesMapper = Mappers.getMapper(ClothesMapper.class);


    @InjectMocks
    private ClothesServiceImpl clothesService;

    @DisplayName("옷장 안에 있는 옷의 개수를 반환한다.")
    @Test
    void getSizeByCloset() {
        final Closet closet = new Closet("c1", true, null);
        when(this.clothesRepository.countByCloset(any())).thenReturn(1L);
        assertEquals(1, this.clothesService.getSizeByCloset(closet));
        verify(this.clothesRepository, atMostOnce()).countByCloset(any());
    }


    @DisplayName("옷장 필터링 기능")
    @Test
    void search() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", new FileInputStream("./README.md"));

        //1.옷장 생성
        Closet closet = new Closet("c1", true, null);
        Closet result = closetRepository.save(closet);

        MoodTag mTag = new MoodTag("무드");
        EventTag eTag = new EventTag("등산");
        SeasonTag sTag = new SeasonTag("가을");
        SeasonTag sTag2= new SeasonTag("봄");

        MoodTag mood = moodTagRepository.save(mTag);
        SeasonTag season = seasonTagRepository.save(sTag);
        SeasonTag season2 = seasonTagRepository.save(sTag2);
        EventTag event = eventTagRepository.save(eTag);

        List<Long> moodList = new ArrayList<>();
        moodList.add(mood.getId());

        List<Long> seasonList = new ArrayList<>();
        seasonList.add(season.getId());
        seasonList.add(season2.getId());

        List<Long> eventList = new ArrayList<>();
        eventList.add(event.getId());

        ClothesDto.ClothesUploadRequest req = new ClothesDto.ClothesUploadRequest(
                result.getId(),moodList, seasonList, eventList, file);

        ClothesDto.ClothesUploadResponse save = clothesService.save(req);

        Assertions.assertThat(save.getClosetId()).isEqualTo(closet.getId());

    }


}