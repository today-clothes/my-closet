package com.oclothes.domain.user.controller;

import com.oclothes.BaseWebMvcTest;
import com.oclothes.domain.user.service.EmailAuthenticationCodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.mockito.Mockito.*;

@WebMvcTest(EmailAuthenticationController.class)
public class EmailAuthenticationControllerTest extends BaseWebMvcTest {

    @MockBean
    private EmailAuthenticationCodeService emailAuthenticationCodeService;

    @DisplayName("메일 인증에 성공한다.")
    @Test
    void emailAuthCodeAuthenticationSuccessTest() throws Exception {
        String email = "test@gmial.com";
        String code = "ABCDEFG";
        SignUpResponse signUpResponse = new SignUpResponse(email);

        when(this.emailAuthenticationCodeService.emailAuthentication(email, code)).thenReturn(signUpResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/email-auth?email={email}&code={code}", email, code))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.view().name("sign-up-success"))
                .andDo(MockMvcResultHandlers.print());
        verify(this.emailAuthenticationCodeService, atMostOnce()).emailAuthentication(email, code);
    }

}
