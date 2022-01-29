package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.domain.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Long>, ClothesSupportRepository {
    List<Clothes> findAllByCloset(Closet closet);

    List<Clothes> findByContentContaining(String keyword);

    long countByCloset(@NonNull Closet closet);
}
