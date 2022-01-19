package com.oclothes.domain.clothes.domain;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Clothes extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Closet closet;

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClothesSeasonTag> seasonTags = new HashSet<>();

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClothesEventTag> eventTags = new HashSet<>();

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClothesMoodTag> moodTags = new HashSet<>();

    private String imgUrl;

    @Builder
    public Clothes(Closet closet, String imgUrl) {
        this.closet = closet;
        this.imgUrl = imgUrl;
    }

    public void setCloset(Closet closet) {
        if (Objects.nonNull(this.closet)) this.closet.deleteCloth(this);
        this.closet = closet;
    }

}
