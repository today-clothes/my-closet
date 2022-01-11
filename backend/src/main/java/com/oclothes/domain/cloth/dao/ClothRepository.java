package com.oclothes.domain.cloth.dao;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.cloth.domain.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClothRepository extends JpaRepository<Cloth, Long> {
    List<Cloth> findByCloset(Closet closet);
}
