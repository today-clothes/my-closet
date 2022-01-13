package com.oclothes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclothes.global.config.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public abstract class BaseWebMvcTest extends BaseTest {
    @Autowired
    protected MockMvc mockMvc;

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    protected JwtProvider jwtProvider;
}
