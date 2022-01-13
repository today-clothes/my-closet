package com.oclothes.domain.user.domain;

import com.oclothes.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest extends BaseTest {

    @Test
    void getHostTest() {
        String domain = new Email("test@gmail.com").getHost();
        Assertions.assertEquals("gmail.com", domain);
    }

    @Test
    void getIdTest() {
        String id = new Email("test@gmail.com").getId();
        Assertions.assertEquals("test", id);
    }

}