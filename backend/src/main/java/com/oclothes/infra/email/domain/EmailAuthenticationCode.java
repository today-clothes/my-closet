package com.oclothes.infra.email.domain;

import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EmailAuthenticationCode extends BaseEntity {

    private String code;

}
