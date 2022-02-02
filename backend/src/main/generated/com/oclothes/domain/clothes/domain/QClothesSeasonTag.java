package com.oclothes.domain.clothes.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClothesSeasonTag is a Querydsl query type for ClothesSeasonTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClothesSeasonTag extends EntityPathBase<ClothesSeasonTag> {

    private static final long serialVersionUID = 120679857L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClothesSeasonTag clothesSeasonTag = new QClothesSeasonTag("clothesSeasonTag");

    public final QClothesTag _super;

    // inherited
    public final QClothes clothes;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final com.oclothes.domain.tag.domain.QSeasonTag tag;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QClothesSeasonTag(String variable) {
        this(ClothesSeasonTag.class, forVariable(variable), INITS);
    }

    public QClothesSeasonTag(Path<? extends ClothesSeasonTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClothesSeasonTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClothesSeasonTag(PathMetadata metadata, PathInits inits) {
        this(ClothesSeasonTag.class, metadata, inits);
    }

    public QClothesSeasonTag(Class<? extends ClothesSeasonTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QClothesTag(type, metadata, inits);
        this.clothes = _super.clothes;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.tag = inits.isInitialized("tag") ? new com.oclothes.domain.tag.domain.QSeasonTag(forProperty("tag")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

