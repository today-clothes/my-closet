package com.oclothes.domain.tag.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MoodTag extends Tag {
    public MoodTag(String name) {
        super(name);
    }
}
