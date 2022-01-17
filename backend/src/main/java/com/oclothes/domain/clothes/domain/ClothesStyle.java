package com.oclothes.domain.clothes.domain;

import com.oclothes.domain.style.domain.Style;
import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClothesStyle extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Clothes clothes;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Style style;

}
