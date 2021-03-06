package com.oclothes.domain.clothes.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.clothes.dto.ClothesMapperSupport;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.global.dto.SliceDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClothesServiceImplTest extends BaseTest {


    @Mock
    private ClothesRepository clothesRepository;

    @Mock
    private FileService fileService;

    @Spy
    @InjectMocks
    private ClothesMapperSupport clothesMapperSupport;

    @Spy
    @InjectMocks
    private ClothesMapper clothesMapper = Mappers.getMapper(ClothesMapper.class);


    @InjectMocks
    private ClothesServiceImpl clothesService;

    private static MockedStatic<SecurityUtils> securityUtilsMock;

    @BeforeAll
    static void beforeAll() {
        User user = User.builder().build();
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getLoggedInUser).thenReturn(user);
    }

    @AfterAll
    static void afterAll() {
        securityUtilsMock.close();
    }

    @DisplayName("옷장 안에 있는 옷의 개수를 반환한다.")
    @Test
    void getSizeByClosetTest() {
        final Closet closet = new Closet("c1", null);
        when(this.clothesRepository.countByCloset(any())).thenReturn(1L);
        assertEquals(1, this.clothesService.getSizeByCloset(closet));
        verify(this.clothesRepository, atMostOnce()).countByCloset(any());
    }

    @DisplayName("ID에 해당하는 옷을 삭제한다.")
    @Test
    void deleteByIdTest() {
        final Clothes clothes = Clothes.builder().imgUrl("url").build();

        when(this.clothesRepository.findById(any())).thenReturn(Optional.of(clothes));
        doNothing().when(this.fileService).delete(any());
        doNothing().when(this.clothesRepository).deleteById(any());

        this.clothesService.deleteById(1L);
        verify(this.clothesRepository, atMostOnce()).findById(any());
        verify(this.fileService, atMostOnce()).delete(any());
        verify(this.clothesRepository, atMostOnce()).deleteById(any());
    }

    @DisplayName("옷 공개 상태가 true일 경우 false로 변경한다.")
    @Test
    void changeLockStatusFalseTest() {
        final long id = 1L;
        final Clothes clothes = Clothes.builder().locked(true).build();

        when(this.clothesRepository.findByIdAndUser(any(), any())).thenReturn(Optional.of(clothes));

        final ClothesDto.DefaultResponse response = this.clothesService.changeLockStatus(1L);
        assertFalse(response.isLocked());
    }

    @DisplayName("옷 공개 상태가 false일 경우 true로 변경한다.")
    @Test
    void changeLockStatusTrueTest() {
        final long id = 1L;
        final Clothes clothes = Clothes.builder().locked(false).build();

        when(this.clothesRepository.findByIdAndUser(any(), any())).thenReturn(Optional.of(clothes));

        final ClothesDto.DefaultResponse response = this.clothesService.changeLockStatus(1L);
        assertTrue(response.isLocked());
    }

    @DisplayName("옷 공개 상태만 검색 결과에서 반환한다.")
    @Test
    void showNonLockedClothesTest() throws NoSuchFieldException, IllegalAccessException {
        final Closet closet = Closet.builder().build();
        final Field closetId = closet.getClass().getSuperclass().getDeclaredField("id");
        closetId.setAccessible(true);
        closetId.set(closet, 1L);
        final Clothes clothes1 = Clothes.builder().closet(closet).locked(true).build();
        final PageRequest pageRequest = PageRequest.of(0, 2);
        SliceImpl<Clothes> slice = new SliceImpl<>(List.of(clothes1), pageRequest, true);

        this.injectClothesMapper();
        when(this.clothesRepository.searchByTag(any(), any(), any())).thenReturn(slice);

        final SliceDto<ClothesDto.SearchResponse> result = this.clothesService.search(null, pageRequest);
        verify(this.clothesRepository, atMostOnce()).searchByTag(any(), any(), any());
        assertEquals(1, result.getContentsCount());
    }

    private void injectClothesMapper() {
        try {
            Field clothesMapper = this.clothesService.getClass().getDeclaredField("clothesMapper");
            clothesMapper.setAccessible(true);
            clothesMapper.set(this.clothesService, this.clothesMapper);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}