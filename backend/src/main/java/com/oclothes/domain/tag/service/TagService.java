package com.oclothes.domain.tag.service;

import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.tag.dto.TagDto;

import java.util.Collection;
import java.util.List;

public interface TagService {
    TagDto.AllResponse getAll();

    List<MoodTag> findAllByMoodTagIds(Collection<Long> ids);
}
