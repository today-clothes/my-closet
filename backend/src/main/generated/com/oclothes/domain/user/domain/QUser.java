package com.oclothes.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1071719924L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.oclothes.global.entity.QBaseEntity _super = new com.oclothes.global.entity.QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final ListPath<com.oclothes.domain.closet.domain.Closet, com.oclothes.domain.closet.domain.QCloset> closets = this.<com.oclothes.domain.closet.domain.Closet, com.oclothes.domain.closet.domain.QCloset>createList("closets", com.oclothes.domain.closet.domain.Closet.class, com.oclothes.domain.closet.domain.QCloset.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QEmail email;

    public final QEmailAuthenticationCode emailAuthenticationCode;

    public final ComparablePath<Character> gender = createComparable("gender", Character.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final EnumPath<User.Role> role = createEnum("role", User.Role.class);

    public final EnumPath<User.Status> status = createEnum("status", User.Status.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.email = inits.isInitialized("email") ? new QEmail(forProperty("email")) : null;
        this.emailAuthenticationCode = inits.isInitialized("emailAuthenticationCode") ? new QEmailAuthenticationCode(forProperty("emailAuthenticationCode")) : null;
    }

}

