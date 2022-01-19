package com.oclothes.domain.clothes.domain;

import com.oclothes.domain.tag.domain.MoodTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class ClothesMoodTag extends ClothesTag<MoodTag> {
    public ClothesMoodTag(Clothes clothes, MoodTag tag) {
        super(clothes, tag);
    }
}
