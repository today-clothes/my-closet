package com.oclothes.domain.tag.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMoodTag is a Querydsl query type for MoodTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMoodTag extends EntityPathBase<MoodTag> {

    private static final long serialVersionUID = 1043469039L;

    public static final QMoodTag moodTag = new QMoodTag("moodTag");

    public final QTag _super = new QTag(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMoodTag(String variable) {
        super(MoodTag.class, forVariable(variable));
    }

    public QMoodTag(Path<? extends MoodTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMoodTag(PathMetadata metadata) {
        super(MoodTag.class, metadata);
    }

}

