package com.oclothes.domain.user.domain;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    public enum Status {
        WAIT, NORMAL, WITHDRAW
    }

    public enum Role {
        USER, ADMIN
    }

    @Embedded
    private Email email;

    private String password;

    private String nickname;

    private Integer height;

    private Integer weight;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Closet> closets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserStyle> userStyles = new ArrayList<>();

    @Builder
    public User(Email email, String password, String nickname, Integer height, Integer weight, Status status, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.height = height;
        this.weight = weight;
        this.status = status;
        this.role = role;
    }

    public void addCloset(Closet closet) {
        this.closets.add(closet);
    }

    public void addUserStyle(UserStyle userStyle) {
        this.userStyles.add(userStyle);
    }

    public void deleteCloset(Closet closet) {
        this.closets.remove(closet);
    }

    public void deleteUserStyle(UserStyle userStyle) {
        this.userStyles.remove(userStyle);
    }

}

