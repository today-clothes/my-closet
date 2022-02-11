package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.oclothes.domain.clothes.domain.QClothes.clothes;
import static com.oclothes.domain.clothes.domain.QClothesEventTag.clothesEventTag;
import static com.oclothes.domain.clothes.domain.QClothesMoodTag.clothesMoodTag;
import static com.oclothes.domain.clothes.domain.QClothesSeasonTag.clothesSeasonTag;
import static com.oclothes.domain.clothes.dto.ClothesDto.*;
import static java.util.Objects.isNull;


@RequiredArgsConstructor
@Repository
public class ClothesSupportRepositoryImpl implements ClothesSupportRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Clothes> searchByTag(SearchRequest request, User user, String keyword, Pageable pageable) {
        List<Long> seasonTagIds = new ArrayList<>();
        List<Long> eventTagIds = new ArrayList<>();
        List<Long> moodTagIds = new ArrayList<>();

        if(!isNull(request)){
            seasonTagIds = request.getSeasonTagIds();
            eventTagIds = request.getEventTagIds();
            moodTagIds = request.getMoodTagIds();
        }

        List<Clothes> content = jpaQueryFactory.selectFrom(clothes).distinct()
                .leftJoin(clothes.seasonTags, clothesSeasonTag)
                .leftJoin(clothes.eventTags, clothesEventTag)
                .leftJoin(clothes.moodTags, clothesMoodTag)
                .where( userIdEq(user),
                        closetIdEq(request),
                        contentsEq(keyword),
                        tagsEq(seasonTagIds, isSeasonTag),
                        tagsEq(eventTagIds, isEventTag),
                        tagsEq(moodTagIds, isMoodTag))
                .orderBy(clothes.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
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

    private BooleanExpression closetIdEq(SearchRequest req) {
        return isNull(req) || isNull(req.getClosetId()) ? null : clothes.closet.id.eq(req.getClosetId());
    }

    private BooleanExpression userIdEq(User user) {
        return isNull(user) ? null : clothes.user.id.eq(user.getId());
    }

    private BooleanBuilder contentsEq(String keyword) {
        return isNull(keyword) ? null : new BooleanBuilder().and(clothes.content.contains(keyword)).and(clothes.locked.eq(false));
    }

    private BooleanBuilder tagsEq(List<Long> ids, Function<Long, BooleanExpression> isTag) {
        BooleanBuilder builder = new BooleanBuilder();
        Optional.ofNullable(ids).orElseGet(Collections::emptyList).forEach(id -> builder.or(isTag.apply(id)));
        return builder;
    }

    private final Function<Long, BooleanExpression> isSeasonTag = id ->
            isNull(id) ? null : clothesSeasonTag.tag.id.eq(id);

    private final Function<Long, BooleanExpression> isEventTag = id ->
            isNull(id) ? null : clothesEventTag.tag.id.eq(id);

    private final Function<Long, BooleanExpression> isMoodTag = id ->
            isNull(id) ? null : clothesMoodTag.id.eq(id);
}
