package com.oclothes.domain.style.service;

import com.oclothes.domain.style.domain.Style;
import com.oclothes.domain.style.dto.StyleDto;

import java.util.List;

public interface StyleService {
    List<StyleDto.Response> findAll();

    List<Style> findAllById(Iterable<Long> ids);
}
