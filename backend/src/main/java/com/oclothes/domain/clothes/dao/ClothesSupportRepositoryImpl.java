package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
                .where(hasClosetId(request.getClosetId()))
                .join(clothes.seasonTags, clothesSeasonTag)
                .where(this.isSeasonTag(request.getSeasonTagIds()))
                .join(clothes.eventTags, clothesEventTag)
                .where(this.isEventTag(request.getEventTagIds()))
                .join(clothes.moodTags, clothesMoodTag)
                .where(isMoodTag(request.getMoodTagIds()))
                .fetch();
    }

    private BooleanExpression hasClosetId(Long closetId) {
        if (Objects.isNull(closetId)) return null;
        return clothes.closet.id.eq(closetId);
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
