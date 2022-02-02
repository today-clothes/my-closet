package com.oclothes.domain.clothes.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClothesMoodTag is a Querydsl query type for ClothesMoodTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClothesMoodTag extends EntityPathBase<ClothesMoodTag> {

    private static final long serialVersionUID = -83137635L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClothesMoodTag clothesMoodTag = new QClothesMoodTag("clothesMoodTag");

    public final QClothesTag _super;

    // inherited
    public final QClothes clothes;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final com.oclothes.domain.tag.domain.QMoodTag tag;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QClothesMoodTag(String variable) {
        this(ClothesMoodTag.class, forVariable(variable), INITS);
    }

    public QClothesMoodTag(Path<? extends ClothesMoodTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClothesMoodTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClothesMoodTag(PathMetadata metadata, PathInits inits) {
        this(ClothesMoodTag.class, metadata, inits);
    }

    public QClothesMoodTag(Class<? extends ClothesMoodTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QClothesTag(type, metadata, inits);
        this.clothes = _super.clothes;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.tag = inits.isInitialized("tag") ? new com.oclothes.domain.tag.domain.QMoodTag(forProperty("tag")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

