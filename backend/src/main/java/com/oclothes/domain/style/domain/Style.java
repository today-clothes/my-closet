package com.oclothes.domain.style.domain;

import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Style extends BaseEntity {

    private String name;

}
