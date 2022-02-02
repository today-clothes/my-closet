package com.oclothes.domain.tag.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeasonTag is a Querydsl query type for SeasonTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeasonTag extends EntityPathBase<SeasonTag> {

    private static final long serialVersionUID = 457934979L;

    public static final QSeasonTag seasonTag = new QSeasonTag("seasonTag");

    public final QTag _super = new QTag(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSeasonTag(String variable) {
        super(SeasonTag.class, forVariable(variable));
    }

    public QSeasonTag(Path<? extends SeasonTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeasonTag(PathMetadata metadata) {
        super(SeasonTag.class, metadata);
    }

}

