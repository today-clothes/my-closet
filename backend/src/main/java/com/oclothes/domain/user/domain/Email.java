package com.oclothes.domain.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {

    @Column(name = "email", unique = true)
    private String value;

    public String getHost() {
        return this.value.substring(this.value.indexOf("@") + 1);
    }

    public String getId() {
        return this.value.substring(0, this.value.indexOf("@"));
    }

}
