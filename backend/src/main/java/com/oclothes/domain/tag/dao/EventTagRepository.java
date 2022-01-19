package com.oclothes.domain.tag.dao;

import com.oclothes.domain.tag.domain.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTagRepository extends JpaRepository<EventTag, Long> {
}
