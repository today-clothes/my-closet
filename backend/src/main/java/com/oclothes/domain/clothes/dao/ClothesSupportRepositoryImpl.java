package com.oclothes.domain.clothes.dao;

import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.user.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

import static com.oclothes.domain.clothes.domain.QClothes.clothes;
import static com.oclothes.domain.clothes.domain.QClothesEventTag.clothesEventTag;
import static com.oclothes.domain.clothes.domain.QClothesMoodTag.clothesMoodTag;
import static com.oclothes.domain.clothes.domain.QClothesSeasonTag.clothesSeasonTag;
import static com.oclothes.domain.clothes.dto.ClothesDto.SearchRequest;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;


@RequiredArgsConstructor
@Repository
public class ClothesSupportRepositoryImpl implements ClothesSupportRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Clothes> searchByTag(SearchRequest request, User user, Pageable pageable) {
        List<Clothes> content = jpaQueryFactory.selectFrom(clothes)
                .leftJoin(clothes.seasonTags, clothesSeasonTag)
                .leftJoin(clothes.eventTags, clothesEventTag)
                .leftJoin(clothes.moodTags, clothesMoodTag)
                .distinct()
                .where(userIdEq(user),
                        closetIdEq(request.getClosetId()),
                        contentContains(request.getKeyword()),
                        tagsIdEq(request.getSeasonTagIds(), seasonTagIdEq),
                        tagsIdEq(request.getEventTagIds(), eventTagIdEq),
                        tagsIdEq(request.getMoodTagIds(), moodTagIdEq))
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

    private BooleanExpression closetIdEq(Long id) {
        return isNull(id) ? null : clothes.closet.id.eq(id);
    }

    private BooleanExpression userIdEq(User user) {
        return isNull(user) ? null : clothes.user.id.eq(user.getId());
    }

    private BooleanExpression contentContains(String keyword) {
        return hasText(keyword) ? clothes.content.contains(keyword).and(clothes.locked.isFalse()) : null;
    }

    private BooleanBuilder tagsIdEq(List<Long> ids, Function<Long, BooleanExpression> tagIdEq) {
        if (isNull(ids) || ids.isEmpty()) return null;
        BooleanBuilder builder = new BooleanBuilder();
        ids.forEach(id -> builder.or(tagIdEq.apply(id)));
        return builder;
    }

    private final Function<Long, BooleanExpression> seasonTagIdEq = id ->
            isNull(id) ? null : clothesSeasonTag.tag.id.eq(id);

    private final Function<Long, BooleanExpression> eventTagIdEq = id ->
            isNull(id) ? null : clothesEventTag.tag.id.eq(id);

    private final Function<Long, BooleanExpression> moodTagIdEq = id ->
            isNull(id) ? null : clothesMoodTag.id.eq(id);
}
