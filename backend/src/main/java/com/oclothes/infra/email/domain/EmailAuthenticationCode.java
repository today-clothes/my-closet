package com.oclothes.infra.email.domain;

import com.oclothes.domain.user.domain.User;
import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EmailAuthenticationCode extends BaseEntity {

    @OneToOne
    private User user;

    private String code;

}
