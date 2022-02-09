package com.oclothes.domain.user.domain;

import com.amazonaws.util.StringUtils;
import com.oclothes.domain.user.exception.AlreadyEmailAuthenticationException;
import com.oclothes.domain.user.exception.EmailAuthenticationCodeTooManyRequestException;
import com.oclothes.domain.user.exception.UserStatusIsWithdrawException;
import com.oclothes.domain.user.exception.WrongEmailAuthenticationCodeException;
import com.oclothes.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EmailAuthenticationCode extends BaseEntity {

    @OneToOne
    private User user;

    private String code;

    public void setCode(String code) {
        if (StringUtils.isNullOrEmpty(this.code)) {
            final int retryLimitMinutes = 3;
            if (ChronoUnit.MINUTES.between(this.getUpdatedAt(), LocalDateTime.now()) < retryLimitMinutes)
                throw new EmailAuthenticationCodeTooManyRequestException();
        }
        this.code = code;
    }

    public User authentication(String code) {
        validateCode(code);
        return this.user.emailAuthenticationSuccess();
    }

    private void validateCode(String code) {
        switch (this.user.getStatus()) {
            case NORMAL: throw new AlreadyEmailAuthenticationException();
            case WITHDRAW: throw new UserStatusIsWithdrawException();
        }
        if (!this.code.equals(code)) throw new WrongEmailAuthenticationCodeException();
    }

}
