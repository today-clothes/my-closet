package com.oclothes.domain.closet.domain;

import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
public class Closet extends BaseEntity {

    private String name;

    @Setter(value = AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "closet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clothes> clothes = new ArrayList<>();

    @Builder
    public Closet(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void addClothes(Clothes clothes) {
        this.clothes.add(clothes);
    }

    public void deleteClothes(Clothes clothes) {
        this.clothes.remove(clothes);
    }
}
