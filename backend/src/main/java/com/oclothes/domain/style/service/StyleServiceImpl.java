package com.oclothes.domain.style.service;

import com.oclothes.domain.style.dao.StyleRepository;
import com.oclothes.domain.style.domain.Style;
import com.oclothes.domain.style.dto.StyleDto;
import com.oclothes.domain.style.dto.StyleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StyleServiceImpl implements StyleService {
    private final StyleRepository styleRepository;
    private final StyleMapper styleMapper;

    @Override
    public List<StyleDto.Response> findAll() {
        return this.styleMapper.toResponse(this.styleRepository.findAll());
    }

    @Override
    public List<Style> findAllById(Iterable<Long> ids) {
        return this.styleRepository.findAllById(ids);
    }
}
