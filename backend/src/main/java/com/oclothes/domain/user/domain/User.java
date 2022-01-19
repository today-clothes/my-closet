package com.oclothes.domain.user.domain;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.user.exception.EmailAuthenticationCodeNotFoundException;
import com.oclothes.domain.user.exception.EmailAuthenticationCodeTooManyRequestException;
import com.oclothes.domain.user.exception.UserExceptionMessage;
import com.oclothes.domain.user.exception.WrongEmailAuthenticationCodeException;
import com.oclothes.global.entity.BaseEntity;
import com.oclothes.global.error.exception.UserStatusException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private Integer height;

    private Integer weight;

    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Closet> closets = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private EmailAuthenticationCode emailAuthenticationCode;

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

    public void deleteCloset(Closet closet) {
        this.closets.remove(closet);
    }

    public void setEmailAuthenticationCode(EmailAuthenticationCode emailAuthenticationCode) {
        if (Objects.nonNull(this.emailAuthenticationCode)) {
            final int retryLimitMinutes = 3;
            if (ChronoUnit.MINUTES.between(this.emailAuthenticationCode.getUpdatedAt(), LocalDateTime.now()) < retryLimitMinutes)
                throw new EmailAuthenticationCodeTooManyRequestException();
        }
        this.emailAuthenticationCode = emailAuthenticationCode;
    }

    public User emailAuthentication(String code) {
        if (!this.getEmailAuthenticationCode().getCode().equals(code)) throw new WrongEmailAuthenticationCodeException();
        return this.successEmailAuthentication();
    }

    private EmailAuthenticationCode getEmailAuthenticationCode() {
        if (this.status.equals(User.Status.NORMAL))
            throw new UserStatusException(UserExceptionMessage.USER_STATUS_IS_ALREADY_NORMAL.getMessage());
        if (Objects.isNull(this.emailAuthenticationCode)) throw new EmailAuthenticationCodeNotFoundException();
        return emailAuthenticationCode;
    }

    private User successEmailAuthentication() {
        this.status = Status.NORMAL;
        this.emailAuthenticationCode = null;
        this.addCloset(new Closet("나의 첫 옷장", false, this));
        return this;
    }

}

