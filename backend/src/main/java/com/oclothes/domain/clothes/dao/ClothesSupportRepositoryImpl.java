package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.oclothes.domain.clothes.domain.QClothes.clothes;
import static com.oclothes.domain.clothes.domain.QClothesEventTag.clothesEventTag;
import static com.oclothes.domain.clothes.domain.QClothesMoodTag.clothesMoodTag;
import static com.oclothes.domain.clothes.domain.QClothesSeasonTag.clothesSeasonTag;

@RequiredArgsConstructor
@Service
public class ClothesSupportRepositoryImpl implements ClothesSupportRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Clothes> search(ClothesDto.SearchRequest request) {
        return this.jpaQueryFactory.selectFrom(clothes)
                .join(clothes.seasonTags, clothesSeasonTag)
                .where(clothes.closet.id.eq(request.getClosetId()), this.isSeasonTag(request.getSeasonTagIds()))
                .join(clothes.eventTags, clothesEventTag)
                .where(this.isEventTag(request.getEventTagIds()))
                .join(clothes.moodTags, clothesMoodTag)
                .where(isMoodTag(request.getMoodTagIds()))
                .fetch();
    }

    private BooleanBuilder isSeasonTag(List<Long> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) return null;
        final BooleanBuilder builder = new BooleanBuilder();
        ids.forEach(id -> builder.or(clothesSeasonTag.tag.id.eq(id)));
        return builder;
    }

    private BooleanBuilder isEventTag(List<Long> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) return null;
        final BooleanBuilder builder = new BooleanBuilder();
        ids.forEach(id -> builder.or(clothesEventTag.tag.id.eq(id)));
        return builder;
    }

    private BooleanBuilder isMoodTag(List<Long> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) return null;
        final BooleanBuilder builder = new BooleanBuilder();
        ids.forEach(id -> builder.or(clothesMoodTag.tag.id.eq(id)));
        return builder;
    }
}
