package com.oclothes.domain.tag.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.dto.TagDto;
import com.oclothes.domain.tag.dto.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class TagServiceImplTest extends BaseTest {
    @Mock
    private SeasonTagRepository seasonTagRepository;
    @Mock
    private MoodTagRepository moodTagRepository;
    @Mock
    private EventTagRepository eventTagRepository;
    @Mock
    private TagMapper tagMapper;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void getAllTest() {
        when(this.seasonTagRepository.findAll()).thenReturn(Collections.emptyList());
        when(this.moodTagRepository.findAll()).thenReturn(Collections.emptyList());
        when(this.eventTagRepository.findAll()).thenReturn(Collections.emptyList());
        when(this.tagMapper.toResponse(anyList())).thenReturn(Collections.emptyList());

        final TagDto.AllResponse result = this.tagService.getAll();

        Assertions.assertEquals(0, result.getEventTags().size());
    }
}