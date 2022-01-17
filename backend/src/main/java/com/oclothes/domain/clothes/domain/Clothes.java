package com.oclothes.domain.clothes.domain;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Clothes extends BaseEntity {

    public enum Season {
        SPRING, SUMMER, FALL, WINTER, NONE
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Closet closet;

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClothesStyle> clothesStyles = new ArrayList<>();

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Season season;

    private String location;

    private boolean bookmarked = false;

    @Builder
    public Clothes(Closet closet, String imgUrl, Season season, String location) {
        this.closet = closet;
        this.imgUrl = imgUrl;
        this.season = season;
        this.location = location;
    }

    public Clothes addAllClothStyle(List<ClothesStyle> clothesStyles) {
        clothesStyles.forEach(c -> c.setClothes(this));
        this.clothesStyles.addAll(clothesStyles);
        return this;
    }

    public void setCloset(Closet closet) {
        if (Objects.nonNull(this.closet)) this.closet.deleteCloth(this);
        this.closet = closet;
    }

    public void deleteClothStyle(ClothesStyle clothesStyle) {
        this.clothesStyles.remove(clothesStyle);
    }

}
