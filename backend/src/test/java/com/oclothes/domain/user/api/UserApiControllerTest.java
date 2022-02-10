package com.oclothes.domain.user.api;

import com.oclothes.BaseWebMvcTest;
import com.oclothes.domain.user.domain.UserPersonalInformation;
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

import java.util.Set;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        SignUpRequest signUpRequest = this.createSignUpRequest(email, password);
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
        SignUpRequest signUpRequest = this.createSignUpRequest(email, password);
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

    @DisplayName("닉네임, 성별, 나이 변경에 성공한다.")
    @WithMockUser
    @Test
    void updateAccountTest() throws Exception {
        UserDto.AccountUpdateRequest request = new UserDto.AccountUpdateRequest("test", UserPersonalInformation.Gender.MALE, 25);
        mockMvc.perform(patch("/users/my-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("완료")))
                .andDo(print());
        verify(this.userService, atMostOnce()).updateAccount(request);
    }

    private SignUpRequest createSignUpRequest(String email, String password) {
        return new SignUpRequest(email, password, "HB", UserPersonalInformation.Gender.FEMALE, 20, 160, 50, Set.of(1L, 2L));
    }

    @DisplayName("키, 몸무게 변경에 성공한다.")
    @WithMockUser
    @Test
    void updateProfileTest() throws Exception{
        UserDto.ProfileUpdateRequest request = new UserDto.ProfileUpdateRequest(200, 100);
        mockMvc.perform(patch("/users/my-profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("완료")))
                .andDo(print());
        verify(this.userService, atMostOnce()).updateProfile(request);
    }

    @DisplayName("회원 정보 조회에 성공한다.")
    @WithMockUser
    @Test
    void getUserTest() throws Exception{
        String email = "test@test.com";
        String nickname = "test";
        UserPersonalInformation.Gender gender = UserPersonalInformation.Gender.MALE;
        Integer age = 20;
        Integer height = 200;
        Integer weight = 100;
        UserDto.GetUserResponse response = new UserDto.GetUserResponse(email, nickname, gender, age, height, weight);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("완료")))
                .andDo(print());
        verify(this.userService, atMostOnce()).getUser();
    }
}