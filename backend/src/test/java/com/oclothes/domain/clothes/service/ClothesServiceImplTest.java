package com.oclothes.domain.clothes.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.style.dto.StyleMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClothesServiceImplTest extends BaseTest {

    @Mock
    private ClothesRepository clothesRepository;

    @Mock
    private ClothesMapper clothesMapper;

    @Mock
    private StyleMapper styleMapper;

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
    void save() {
    }
}