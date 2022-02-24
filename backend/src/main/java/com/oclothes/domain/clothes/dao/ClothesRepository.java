package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.domain.UserPersonalInformation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ClothesRepository extends JpaRepository<Clothes, Long>, ClothesSupportRepository {

    @EntityGraph(attributePaths = {"user", "seasonTags", "eventTags", "moodTags"})
    @Query("select c from Clothes c where c.id = :id")
    Optional<Clothes> findClothesDetails(@Param("id") Long id);

    Optional<Clothes> findByIdAndUser(Long id, @NonNull User user);

    long countByCloset(@NonNull Closet closet);

    void deleteAllByIdIn(Collection<Long> ids);

    List<Clothes> findAllByIdIn(Collection<Long> ids);

    @Query("select c from Clothes c " +
            "where c.user.personalInformation.gender = :gender " +
            "and c.user.personalInformation.age between :age - 5 and :age + 5 " +
            "and c.user.personalInformation.height between :height - 5 and :height + 5 " +
            "and c.user.personalInformation.weight between :weight - 5 and :weight + 5")
    List<Clothes> findAllForRecommend(UserPersonalInformation.Gender gender, int age, int height, int weight);

    Optional<Clothes> findFirstByClosetOrderByCreatedAtDesc(Closet closet);
}
