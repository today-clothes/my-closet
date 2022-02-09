package com.oclothes.domain.tag.service;

import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.tag.dto.TagMapper;
import com.oclothes.domain.tag.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<MoodTag> findAllByMoodTagIds(Collection<Long> ids) {
        return ids.stream()
                .map(id -> this.moodTagRepository.findById(id).orElseThrow(TagNotFoundException::new))
                .collect(Collectors.toList());
    }
}
