package com.oclothes.domain.tag.domain;

import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class Tag extends BaseEntity {
    private String name;
}
