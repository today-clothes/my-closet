package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.ClothesTag;
import com.oclothes.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesTagRepository<T extends ClothesTag<? extends Tag>> extends JpaRepository<T, Long> {
}
