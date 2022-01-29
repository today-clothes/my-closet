package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Long>, ClothesSupportRepository {
    List<Clothes> findAllByCloset(Closet closet);

    //키워드로 옷 전체 검색
    List<Clothes> findByContentContaining(String keyword);

    long countByCloset(@NonNull Closet closet);
}
