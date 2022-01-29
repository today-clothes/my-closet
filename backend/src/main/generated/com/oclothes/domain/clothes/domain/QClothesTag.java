package com.oclothes.domain.clothes.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClothesTag is a Querydsl query type for ClothesTag
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QClothesTag extends EntityPathBase<ClothesTag<?>> {

    private static final long serialVersionUID = -1440521452L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClothesTag clothesTag = new QClothesTag("clothesTag");

    public final com.oclothes.global.entity.QBaseEntity _super = new com.oclothes.global.entity.QBaseEntity(this);

    public final QClothes clothes;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final SimplePath<Object> tag = createSimple("tag", Object.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QClothesTag(String variable) {
        this((Class) ClothesTag.class, forVariable(variable), INITS);
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QClothesTag(Path<? extends ClothesTag> path) {
        this((Class) path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClothesTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QClothesTag(PathMetadata metadata, PathInits inits) {
        this((Class) ClothesTag.class, metadata, inits);
    }

    public QClothesTag(Class<? extends ClothesTag<?>> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clothes = inits.isInitialized("clothes") ? new QClothes(forProperty("clothes"), inits.get("clothes")) : null;
    }

}

