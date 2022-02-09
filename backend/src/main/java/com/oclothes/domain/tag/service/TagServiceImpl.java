package com.oclothes.domain.tag.service;

import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.dto.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.oclothes.domain.tag.dto.TagDto.AllResponse;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final SeasonTagRepository seasonTagRepository;
    private final MoodTagRepository moodTagRepository;
    private final EventTagRepository eventTagRepository;
    private final TagMapper tagMapper;

    @Override
    public AllResponse getAll() {
        return new AllResponse(
                this.tagMapper.toResponse(this.seasonTagRepository.findAll()),
                this.tagMapper.toResponse(this.moodTagRepository.findAll()),
                this.tagMapper.toResponse(this.eventTagRepository.findAll())
        );
    }
}
