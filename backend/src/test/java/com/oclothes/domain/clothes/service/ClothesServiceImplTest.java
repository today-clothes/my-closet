package com.oclothes.domain.clothes.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClothesServiceImplTest extends BaseTest {

    @Mock
    private ClothesRepository clothesRepository;

    @Spy
    private ClothesMapper clothesMapper;

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

    @DisplayName("옷장 안에 옷을 저장한다.")
    @Test
    void save() throws NoSuchFieldException, IllegalAccessException {
        final MockMultipartFile file = new MockMultipartFile("file", "".getBytes(StandardCharsets.UTF_8));
        final ClothesDto.ClothesUploadRequest request = new ClothesDto.ClothesUploadRequest(1L, null, null, null, file);
        final Clothes clothes = Clothes.builder().closet(new Closet("c1", false, null)).build();
        final Field id = clothes.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(clothes, 1L);

        when(this.clothesRepository.save(any())).thenReturn(clothes);

        final ClothesDto.ClothesUploadResponse result = this.clothesService.save(request);
        assertEquals(1L, result.getClothesId());
        assertEquals(1L, result.getClothesId());
    }
}