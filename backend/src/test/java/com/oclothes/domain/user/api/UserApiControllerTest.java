package com.oclothes.domain.user.api;

import com.oclothes.BaseWebMvcTest;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.domain.user.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest extends BaseWebMvcTest {

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
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(containsString("gmail")))
                .andDo(print());
    }

    @DisplayName("요구되는 형식에 일치하지 않은 회원가입 요청을 하면 실패한다.")
    @CsvSource({"email1, abcde", "email2, 123"})
    @ParameterizedTest
    void invalidSignUpRequestTest(String email, String password) throws Exception {
        SignUpResponse signUpResponse = new SignUpResponse(email);
        when(this.userService.signUp(any())).thenReturn(signUpResponse);
        SignUpRequest signUpRequest = new SignUpRequest(email, password);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").exists())
                .andExpect(jsonPath("$.errors.email").exists())
                .andExpect(jsonPath("$.errors.password").exists())
                .andDo(print());
    }

    @DisplayName("로그인에 성공한다.")
    @Test
    void loginSuccessTest() throws Exception {
        UserDto.LoginRequest loginRequest = new UserDto.LoginRequest("test@gmail.com", "123456");
        UserDto.LoginResponse loginResponse = new UserDto.LoginResponse("token");
        when(this.userService.login(any())).thenReturn(loginResponse);
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("성공")))
                .andExpect(jsonPath("$.data.accessToken").value(loginResponse.getAccessToken()))
                .andDo(print());
    }

    @DisplayName("회원 프로필 변경에 성공한다.")
    @WithMockUser
    @Test
    void updateProfileTest() throws Exception{
        final Long id = 1L;
        final Character gender = 'M';
        final Integer age = 25;
        final Integer height = 200;
        final Integer weight = 100;
        final UserDto.ProfileUpdateRequest request = new UserDto.ProfileUpdateRequest(id, gender, age, height, weight);
        final UserDto.DefaultResponse response = new UserDto.DefaultResponse(id, gender, age, height, weight);
        when(this.userService.updateProfile(any(), any())).thenReturn(response);
        mockMvc.perform(patch("/users/{id}/profile", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("완료")))
                .andDo(print());
        verify(this.userService, atMostOnce()).updateProfile(id, request);
    }
}