package com.oclothes.domain.clothes.domain;


import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class ClothesTag<T> extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    protected Clothes clothes;

    @ManyToOne
    @JoinColumn(nullable = false)
    protected T tag;

}
