package com.oclothes.domain.user.domain;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.oclothes.domain.user.domain.UserPersonalInformation.Gender;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    public enum Status {
        WAIT, NORMAL, WITHDRAW
    }

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    @Embedded
    private Email email;

    private String password;

    private String nickname;

    @Embedded
    private UserPersonalInformation personalInformation = new UserPersonalInformation();

    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserMoodTag> moodTags = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Closet> closets = new ArrayList<>();

    @Builder
    public User(
            Email email,
            String password,
            String nickname,
            Gender gender,
            Integer age,
            Integer height,
            Integer weight,
            Status status,
            Role role
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.personalInformation.setGender(gender);
        this.personalInformation.setAge(age);
        this.personalInformation.setHeight(height);
        this.personalInformation.setWeight(weight);
        this.status = status;
        this.role = role;
    }

    public User emailAuthenticationSuccess() {
        this.status = Status.NORMAL;
        this.addCloset(new Closet("나의 첫 옷장", this));
        return this;
    }

    public void addCloset(Closet closet) {
        this.closets.add(closet);
    }

    public void deleteCloset(Closet closet) {
        this.closets.remove(closet);
    }

    public void updateUserAccount(UserDto.AccountUpdateRequest request) {
        this.nickname = request.getNickname();
        this.personalInformation.setGender(request.getGender());
        this.personalInformation.setAge(request.getAge());
    }

    public void updateUserProfile(UserDto.ProfileUpdateRequest request){
        this.personalInformation.setHeight(request.getHeight());
        this.personalInformation.setWeight(request.getWeight());
    }

    public User addAllMoodTags(List<MoodTag> moodTags) {
        this.moodTags.addAll(moodTags.stream().map(tag -> new UserMoodTag(this, tag)).collect(Collectors.toSet()));
        return this;
    }
}