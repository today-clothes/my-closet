package com.oclothes.domain.closet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCloset is a Querydsl query type for Closet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCloset extends EntityPathBase<Closet> {

    private static final long serialVersionUID = 399038678L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCloset closet = new QCloset("closet");

    public final com.oclothes.global.entity.QBaseEntity _super = new com.oclothes.global.entity.QBaseEntity(this);

    public final ListPath<com.oclothes.domain.clothes.domain.Clothes, com.oclothes.domain.clothes.domain.QClothes> clothes = this.<com.oclothes.domain.clothes.domain.Clothes, com.oclothes.domain.clothes.domain.QClothes>createList("clothes", com.oclothes.domain.clothes.domain.Clothes.class, com.oclothes.domain.clothes.domain.QClothes.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath locked = createBoolean("locked");

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.oclothes.domain.user.domain.QUser user;

    public QCloset(String variable) {
        this(Closet.class, forVariable(variable), INITS);
    }

    public QCloset(Path<? extends Closet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCloset(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCloset(PathMetadata metadata, PathInits inits) {
        this(Closet.class, metadata, inits);
    }

    public QCloset(Class<? extends Closet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.oclothes.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

