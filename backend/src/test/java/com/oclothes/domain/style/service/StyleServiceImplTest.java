package com.oclothes.domain.style.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.style.dao.StyleRepository;
import com.oclothes.domain.style.domain.Style;
import com.oclothes.domain.style.dto.StyleDto;
import com.oclothes.domain.style.dto.StyleMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StyleServiceImplTest extends BaseTest {

    @Mock
    private StyleRepository styleRepository;

    @Spy
    private StyleMapper styleMapper = Mappers.getMapper(StyleMapper.class);

    @InjectMocks
    private StyleServiceImpl styleService;

    @Test
    void findAll() {
        final List<Style> styles = IntStream.range(0, 10).mapToObj(i -> new Style(Style.TYPE.TPO, "t" + i)).collect(Collectors.toList());

        when(this.styleRepository.findAll()).thenReturn(styles);

        final List<StyleDto.Response> result = this.styleService.findAll();
        assertEquals(10, result.size());
        verify(this.styleRepository, atMostOnce()).findAll();
    }

    @Test
    void findAllById() {
        final List<Style> styles = IntStream.range(0, 3).mapToObj(i -> new Style(Style.TYPE.TPO, "t" + i)).collect(Collectors.toList());
        final List<Long> ids = LongStream.range(0, 3).boxed().collect(Collectors.toList());

        when(this.styleRepository.findAllById(any())).thenReturn(styles);

        final List<Style> result = this.styleService.findAllById(ids);
        assertEquals(ids.size(), result.size());
        verify(this.styleRepository, atMostOnce()).findAllById(any());
    }
}