package com.oclothes.domain.tag.dao;

import com.oclothes.domain.tag.domain.MoodTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodTagRepository extends JpaRepository<MoodTag, Long> {
}
