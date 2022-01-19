package com.oclothes.domain.clothes.domain;

import com.oclothes.domain.tag.domain.EventTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClothesEventTag extends ClothesTag<EventTag> {
    public ClothesEventTag(Clothes clothes, EventTag tag) {
        super(clothes, tag);
    }
}
