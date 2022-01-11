package com.oclothes.domain.style.dao;

import com.oclothes.domain.style.domain.Style;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleRepository extends JpaRepository<Style, Long> {
}
