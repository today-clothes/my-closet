package com.oclothes.domain.clothes.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClothes is a Querydsl query type for Clothes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClothes extends EntityPathBase<Clothes> {

    private static final long serialVersionUID = -1474618714L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClothes clothes = new QClothes("clothes");

    public final com.oclothes.global.entity.QBaseEntity _super = new com.oclothes.global.entity.QBaseEntity(this);

    public final com.oclothes.domain.closet.domain.QCloset closet;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final SetPath<ClothesEventTag, QClothesEventTag> eventTags = this.<ClothesEventTag, QClothesEventTag>createSet("eventTags", ClothesEventTag.class, QClothesEventTag.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath imgUrl = createString("imgUrl");

    public final SetPath<ClothesMoodTag, QClothesMoodTag> moodTags = this.<ClothesMoodTag, QClothesMoodTag>createSet("moodTags", ClothesMoodTag.class, QClothesMoodTag.class, PathInits.DIRECT2);

    public final SetPath<ClothesSeasonTag, QClothesSeasonTag> seasonTags = this.<ClothesSeasonTag, QClothesSeasonTag>createSet("seasonTags", ClothesSeasonTag.class, QClothesSeasonTag.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.oclothes.domain.user.domain.QUser user;

    public QClothes(String variable) {
        this(Clothes.class, forVariable(variable), INITS);
    }

    public QClothes(Path<? extends Clothes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClothes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClothes(PathMetadata metadata, PathInits inits) {
        this(Clothes.class, metadata, inits);
    }

    public QClothes(Class<? extends Clothes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.closet = inits.isInitialized("closet") ? new com.oclothes.domain.closet.domain.QCloset(forProperty("closet"), inits.get("closet")) : null;
        this.user = inits.isInitialized("user") ? new com.oclothes.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

