package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.domain.clothes.domain.Clothes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static com.oclothes.domain.clothes.domain.QClothesEventTag.clothesEventTag;
import static com.oclothes.domain.clothes.domain.QClothesMoodTag.clothesMoodTag;
import static com.oclothes.domain.clothes.domain.QClothesSeasonTag.clothesSeasonTag;
import static com.oclothes.domain.clothes.domain.QClothes.clothes;



@RequiredArgsConstructor
@Repository
public class ClothesSupportRepositoryImpl implements ClothesSupportRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Clothes> searchAllClosetByTag(ClothesDto.SearchRequest request, Pageable pageable){
        List<Clothes> content = jpaQueryFactory.selectFrom(clothes).distinct()
                .leftJoin(clothes.seasonTags, clothesSeasonTag)
                .leftJoin(clothes.eventTags, clothesEventTag)
                .leftJoin(clothes.moodTags, clothesMoodTag)
                .where(userEq(SecurityUtils.getLoggedInUser().getId()),
                        tagsEq(request.getSeasonTagIds(), request.getEventTagIds(), request.getMoodTagIds()))
                .orderBy(clothes.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(content, pageable, hasNextPage(content, pageable));
    }

    @Override
    public Slice<Clothes> searchByTag(ClothesDto.SearchRequest request, Pageable pageable) {
        List<Clothes> content = jpaQueryFactory.selectFrom(clothes).distinct()
                .leftJoin(clothes.seasonTags, clothesSeasonTag)
                .leftJoin(clothes.eventTags, clothesEventTag)
                .leftJoin(clothes.moodTags, clothesMoodTag)
                .where(closetEq(request.getClosetId()),
                        tagsEq(request.getSeasonTagIds(), request.getEventTagIds(), request.getMoodTagIds()))
                .orderBy(clothes.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(content, pageable, hasNextPage(content, pageable));
    }

    private boolean hasNextPage(List<Clothes> content, Pageable pageable) {
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }

    private BooleanExpression closetEq(Long id){
        if (Objects.isNull(id)) return null;
        return clothes.closet.id.eq(id);
    }


    private BooleanExpression userEq(Long id){
        if (Objects.isNull(id)) return null;
        return clothes.user.id.eq(id);
    }

    private BooleanBuilder tagsEq(List<Long> sids, List<Long> eids, List<Long> mids) {
        BooleanBuilder builder = new BooleanBuilder();
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
