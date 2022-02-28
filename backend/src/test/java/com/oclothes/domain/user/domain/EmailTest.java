package com.oclothes.domain.user.domain;

import com.oclothes.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest extends BaseTest {

    @DisplayName("이메일 호스트 가져오기를 성공한다.")
    @Test
    void getHostTest() {
        String domain = new Email("test@gmail.com").getHost();
        Assertions.assertEquals("gmail.com", domain);
    }

    @DisplayName("이메일 아이디 가져오기를 성공한다.")
    @Test
    void getIdTest() {
        String id = new Email("test@gmail.com").getId();
        Assertions.assertEquals("test", id);
    }

}