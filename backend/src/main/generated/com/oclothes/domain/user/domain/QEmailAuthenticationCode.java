package com.oclothes.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailAuthenticationCode is a Querydsl query type for EmailAuthenticationCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailAuthenticationCode extends EntityPathBase<EmailAuthenticationCode> {

    private static final long serialVersionUID = 1790033016L;

    public static final QEmailAuthenticationCode emailAuthenticationCode = new QEmailAuthenticationCode("emailAuthenticationCode");

    public final com.oclothes.global.entity.QBaseEntity _super = new com.oclothes.global.entity.QBaseEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QEmailAuthenticationCode(String variable) {
        super(EmailAuthenticationCode.class, forVariable(variable));
    }

    public QEmailAuthenticationCode(Path<? extends EmailAuthenticationCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailAuthenticationCode(PathMetadata metadata) {
        super(EmailAuthenticationCode.class, metadata);
    }

}

