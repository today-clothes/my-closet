package com.oclothes.domain.clothes.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;

import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.infra.file.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.global.dto.SliceDto;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClothesServiceImplTest extends BaseTest {

    @Mock
    private ClothesRepository clothesRepository;

    @Mock
    private FileService fileService;

    @Spy
    private ClothesMapper clothesMapper = Mappers.getMapper(ClothesMapper.class);

    @InjectMocks
    private ClothesServiceImpl clothesService;

    private static MockedStatic<SecurityUtils> securityUtilsMock;

    @BeforeAll
    static void beforeAll() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
    }

    @AfterAll
    static void afterAll() {
        securityUtilsMock.close();
    }

    @DisplayName("옷장 안에 있는 옷의 개수를 반환한다.")
    @Test
    void getSizeByCloset() {
        final Closet closet = new Closet("c1", null);
        when(this.clothesRepository.countByCloset(any())).thenReturn(1L);
        assertEquals(1, this.clothesService.getSizeByCloset(closet));
        verify(this.clothesRepository, atMostOnce()).countByCloset(any());
    }

    @DisplayName("ID에 해당하는 옷을 삭제한다.")
    @Test
    void name() {
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

}