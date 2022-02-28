package com.oclothes.domain.clothes.domain;

import com.oclothes.domain.tag.domain.SeasonTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClothesSeasonTag extends ClothesTag<SeasonTag> {
    public ClothesSeasonTag(Clothes clothes, SeasonTag tag) {
        super(clothes, tag);
    }
}
