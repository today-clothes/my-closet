package com.oclothes.infra.email.domain;

import com.oclothes.domain.user.domain.User;
import com.oclothes.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EmailAuthenticationCode extends BaseEntity {

    @OneToOne
    private User user;

    private String authenticationCode;

}
