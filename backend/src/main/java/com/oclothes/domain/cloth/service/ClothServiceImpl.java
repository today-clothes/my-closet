package com.oclothes.domain.cloth.service;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.cloth.dao.ClothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClothServiceImpl implements ClothService {
    private final ClothRepository clothRepository;

    @Override
    public long getSizeByCloset(Closet closet) {
        return this.clothRepository.countByCloset(closet);
    }
}
