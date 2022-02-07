package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ClothesRepository extends JpaRepository<Clothes, Long>, ClothesSupportRepository {
    List<Clothes> findAllByCloset(Closet closet);

    Optional<Clothes> findByIdAndUser(Long id, @NonNull User user);

    Slice<Clothes> findByContentContaining(String keyword, Pageable pageable);

    long countByCloset(@NonNull Closet closet);
}
