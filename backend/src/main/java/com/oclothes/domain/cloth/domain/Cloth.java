package com.oclothes.domain.cloth.domain;

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
public class Cloth extends BaseEntity {

    public enum Season {
        SPRING, SUMMER, FALL, WINTER, NONE
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Closet closet;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClothStyle> clothStyles = new ArrayList<>();

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Season season;

    private String location;

    private boolean bookmarked = false;

    @Builder
    public Cloth(Closet closet, String imgUrl, Season season, String location) {
        this.closet = closet;
        this.imgUrl = imgUrl;
        this.season = season;
        this.location = location;
    }

    public void addClothStyle(ClothStyle clothStyle) {
        this.clothStyles.add(clothStyle);
    }

    public void setCloset(Closet closet) {
        if (Objects.nonNull(this.closet)) this.closet.deleteCloth(this);
        this.closet = closet;
    }

    public void deleteClothStyle(ClothStyle clothStyle) {
        this.clothStyles.remove(clothStyle);
    }

}
