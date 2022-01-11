package com.oclothes.domain.closet.domain;

import com.oclothes.domain.cloth.domain.Cloth;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Closet extends BaseEntity {

    private String name;

    private boolean lock = false;

    @Setter(value = AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "closet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cloth> cloths = new ArrayList<>();

    public Closet(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void addCloth(Cloth cloth) {
        this.cloths.add(cloth);
    }

    public void deleteCloth(Cloth cloth) {
        this.cloths.remove(cloth);
    }

}
