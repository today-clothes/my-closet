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

    private boolean locked = false;

    @Setter(value = AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "closet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clothes> clothes = new ArrayList<>();

    @Builder
    public Closet(String name, boolean locked, User user) {
        this.name = name;
        this.locked = locked;
        this.user = user;
    }

    public void addCloth(Clothes clothes) {
        this.clothes.add(clothes);
    }

    public void deleteCloth(Clothes clothes) {
        this.clothes.remove(clothes);
    }

    public Closet changeLockedStatus() {
        this.locked = !this.locked;
        return this;
    }
}
