package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.*;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.oclothes.domain.clothes.domain.QClothes.*;
import static com.oclothes.domain.clothes.domain.QClothesEventTag.clothesEventTag;
import static com.oclothes.domain.clothes.domain.QClothesMoodTag.clothesMoodTag;
import static com.oclothes.domain.clothes.domain.QClothesSeasonTag.clothesSeasonTag;


@RequiredArgsConstructor
@Repository
public class ClothesSupportRepositoryImpl implements ClothesSupportRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final BooleanBuilder builder = new BooleanBuilder();

    //1. 전체(기본) 옷장 태그 필터링
    @Override
    public List<Clothes> searchAllClosetByTag(ClothesDto.SearchRequest request){
        return jpaQueryFactory.selectFrom(clothes).distinct()
                .leftJoin(clothes.seasonTags, clothesSeasonTag)
                .leftJoin(clothes.eventTags, clothesEventTag)
                .leftJoin(clothes.moodTags, clothesMoodTag)
                .where(userEq(SecurityUtils.getLoggedInUser().getId()),
                        tagsEq(request.getSeasonTagIds(), request.getEventTagIds(), request.getMoodTagIds()))
                .fetch();
    }


    //2.개별 옷장 태그 필터링
    @Override
    public List<Clothes> searchByTag(ClothesDto.SearchRequest request) {
        return jpaQueryFactory.selectFrom(clothes).distinct()
                .leftJoin(clothes.seasonTags, clothesSeasonTag)
                .leftJoin(clothes.eventTags, clothesEventTag)
                .leftJoin(clothes.moodTags, clothesMoodTag)
                .where(closetEq(request.getClosetId()),
                        tagsEq(request.getSeasonTagIds(), request.getEventTagIds(), request.getMoodTagIds()))
                .fetch();
    }

    private BooleanExpression closetEq(Long id){
        if (Objects.isNull(id)) return null;
        return clothes.closet.id.eq(id);
    }


    private BooleanExpression userEq(Long id){
        if (Objects.isNull(id)) return null;
        return clothes.user.id.eq(id);
    }

    private BooleanBuilder tagsEq(List<Long> sids, List<Long> eids, List<Long> mids){
        Optional.ofNullable(sids).orElseGet(Collections::emptyList).stream()
                .forEach(id -> builder.or(isSeasonTag(id)));

        Optional.ofNullable(eids).orElseGet(Collections::emptyList).stream()
                .forEach(id -> builder.or(isEventTag(id)));

        Optional.ofNullable(mids).orElseGet(Collections::emptyList).stream()
                .forEach(id -> builder.or(isMoodTag(id)));

        return builder;
    }


    private BooleanBuilder isSeasonTag(Long id){
        if (Objects.isNull(id)) return null;
        return new BooleanBuilder(clothesSeasonTag.tag.id.eq(id));
    }

    private BooleanBuilder isEventTag(Long id){
        if (Objects.isNull(id)) return null;
        return new BooleanBuilder(clothesEventTag.tag.id.eq(id));
    }

    private BooleanBuilder isMoodTag(Long id){
        if (Objects.isNull(id)) return null;
        return new BooleanBuilder(clothesMoodTag.id.eq(id));
    }



}
