package com.oclothes.domain.tag.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventTag is a Querydsl query type for EventTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventTag extends EntityPathBase<EventTag> {

    private static final long serialVersionUID = -1428336044L;

    public static final QEventTag eventTag = new QEventTag("eventTag");

    public final QTag _super = new QTag(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QEventTag(String variable) {
        super(EventTag.class, forVariable(variable));
    }

    public QEventTag(Path<? extends EventTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventTag(PathMetadata metadata) {
        super(EventTag.class, metadata);
    }

}

