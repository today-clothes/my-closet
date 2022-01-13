package com.oclothes.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclothes.domain.user.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    @DisplayName("회원가입에 성공한다.")
    @Test
    void signUpTest() throws Exception {
        String email = "test@gmail.com";
        String password = "123456";
        SignUpResponse signUpResponse = new SignUpResponse(email);
        when(this.userService.signUp(any())).thenReturn(signUpResponse);
        SignUpRequest signUpRequest = new SignUpRequest(email, password);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(Matchers.containsString("gmail")))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("요구되는 형식에 일치하지 않은 회원가입 요청을 하면 실패한다.")
    @CsvSource({"email1, abcde", "email2, 123"})
    @ParameterizedTest
    void invalidSignUpRequestTest(String email, String password) throws Exception {
        SignUpResponse signUpResponse = new SignUpResponse(email);
        when(this.userService.signUp(any())).thenReturn(signUpResponse);
        SignUpRequest signUpRequest = new SignUpRequest(email, password);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.password").exists())
                .andDo(MockMvcResultHandlers.print());
    }

}