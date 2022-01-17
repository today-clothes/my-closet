package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.ClothesStyle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesStyleRepository extends JpaRepository<ClothesStyle, Long> {
}
