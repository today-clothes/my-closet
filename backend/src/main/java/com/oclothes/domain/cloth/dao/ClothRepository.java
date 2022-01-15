package com.oclothes.domain.cloth.dao;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.cloth.domain.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ClothRepository extends JpaRepository<Cloth, Long> {
    List<Cloth> findAllByCloset(Closet closet);

    long countByCloset(@NonNull Closet closet);
}
