package com.oclothes.domain.clothes.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClothesEventTag is a Querydsl query type for ClothesEventTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClothesEventTag extends EntityPathBase<ClothesEventTag> {

    private static final long serialVersionUID = -1993404570L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClothesEventTag clothesEventTag = new QClothesEventTag("clothesEventTag");

    public final QClothesTag _super;

    // inherited
    public final QClothes clothes;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final com.oclothes.domain.tag.domain.QEventTag tag;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QClothesEventTag(String variable) {
        this(ClothesEventTag.class, forVariable(variable), INITS);
    }

    public QClothesEventTag(Path<? extends ClothesEventTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClothesEventTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClothesEventTag(PathMetadata metadata, PathInits inits) {
        this(ClothesEventTag.class, metadata, inits);
    }

    public QClothesEventTag(Class<? extends ClothesEventTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QClothesTag(type, metadata, inits);
        this.clothes = _super.clothes;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.tag = inits.isInitialized("tag") ? new com.oclothes.domain.tag.domain.QEventTag(forProperty("tag")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

