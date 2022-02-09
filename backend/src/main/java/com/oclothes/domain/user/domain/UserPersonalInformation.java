package com.oclothes.domain.user.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserPersonalInformation {
    public enum Gender {
        MALE, FEMALE
    }

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    private Integer height;

    private Integer weight;
}
