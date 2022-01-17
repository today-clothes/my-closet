package com.oclothes.domain.style.domain;

import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Style extends BaseEntity {

    public enum TYPE {
        TPO, SEASON, MOOD
    }

    @Enumerated(EnumType.STRING)
    private TYPE type;

    private String name;

}
