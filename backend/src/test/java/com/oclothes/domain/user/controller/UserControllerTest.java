package com.oclothes.domain.user.controller;

import com.oclothes.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @DisplayName("메일 인증에 성공한다.")
    @Test
    void emailAuthCodeAuthenticationSuccessTest() throws Exception {
        String email = "test@gmial.com";
        String authCode = "ABCDEFG";
        SignUpResponse signUpResponse = new SignUpResponse(email);
        when(this.userService.emailAuthentication(email, authCode)).thenReturn(signUpResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/email-auth/{email}/{authCode}", email, authCode))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.view().name("sign-up-success"))
                .andDo(MockMvcResultHandlers.print());
        verify(this.userService, atMostOnce()).emailAuthentication(email, authCode);
    }

}
