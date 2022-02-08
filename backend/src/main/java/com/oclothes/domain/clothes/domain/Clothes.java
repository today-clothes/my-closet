package com.oclothes.domain.clothes.domain;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.user.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClothesSeasonTag> seasonTags = new HashSet<>();

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClothesEventTag> eventTags = new HashSet<>();

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClothesMoodTag> moodTags = new HashSet<>();

    private boolean locked = false;

    private String styleTitle;

    private String content;

    private String imgUrl;

    @Builder
    public Clothes(Closet closet, User user, boolean locked, String imgUrl) {
        this.closet = closet;
        this.user = user;
        this.locked = locked;
        this.imgUrl = imgUrl;
    }

    public void setCloset(Closet closet) {
        if (Objects.nonNull(this.closet)) this.closet.deleteClothes(this);
        this.closet = closet;
    }

    public Clothes changeLockStatus(){
        this.locked = !this.locked;
        return this;
    }
}
