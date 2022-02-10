package com.oclothes.domain.user.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.tag.service.TagService;
import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.EmailAuthenticationCode;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.domain.UserPersonalInformation;
import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.domain.user.dto.UserMapper;
import com.oclothes.domain.user.exception.AlreadyExistsEmailException;
import com.oclothes.domain.user.exception.AlreadyExistsNicknameException;
import com.oclothes.domain.user.exception.UserExceptionMessage;
import com.oclothes.domain.user.exception.UserStatusIsWaitException;
import com.oclothes.domain.user.util.EmailAuthenticationCodeGenerator;
import com.oclothes.global.config.security.PasswordEncoderConfig;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.infra.email.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;

import java.util.Collections;
import java.util.Optional;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest extends BaseTest {

    @Spy
    PasswordEncoderConfig passwordEncoderConfig;

    @Spy
    @InjectMocks
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailAuthenticationCodeService emailAuthenticationCodeService;

    @Mock
    private EmailService emailService;

    @Mock
    private TagService tagService;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @DisplayName("회원가입 대기 상태인 유저는 UserStatusIsWaitException 엑셉션이 발생한다.")
    @Test
    void userStateIsWaitExceptionTest() {
        SignUpRequest requestDto = this.createSignUpRequest("email@gmail.com", "123456");
        User user = this.userMapper.toEntity(requestDto);
        when(this.userRepository.existsByEmail_Value(any())).thenReturn(true);
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(UserStatusIsWaitException.class, () -> this.userService.signUp(requestDto));
        verify(this.userRepository, atMostOnce()).existsByEmail_Value(any());
    }

    @DisplayName("이미 회원가입이 된 이메일은 AlreadyExistsEmailException이 발생한다.")
    @Test
    void alreadyExistsEmailThrowTest() {
        SignUpRequest requestDto = this.createSignUpRequest("email@gmail.com", "123456");
        User user = this.userMapper.toEntity(requestDto, User.Status.NORMAL);

        when(this.userRepository.existsByEmail_Value(any())).thenReturn(true);
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));

        assertThrows(AlreadyExistsEmailException.class, () ->
                this.userService.signUp(requestDto), UserExceptionMessage.ALREADY_EXISTS_EMAIL.getMessage());
        verify(this.userRepository, atMostOnce()).existsByEmail_Value(any());
    }

    @DisplayName("유저가 회원가입 인증메일을 요청에 성공한다.")
    @Test
    void signUpTest() {
        String email = "test@gmail.com";
        SignUpRequest requestDto = this.createSignUpRequest(email, "123456");
        User user = this.userMapper.toEntity(requestDto, User.Status.WAIT);

        when(this.tagService.findAllByMoodTagIds(any())).thenReturn(Collections.emptyList());
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.emailAuthenticationCodeService.save(any())).thenReturn(new EmailAuthenticationCode(user, EmailAuthenticationCodeGenerator.generateAuthCode()));
        doNothing().when(this.emailService).sendEmail(any(), any(), any());

        SignUpResponse signUpResponse = this.userService.signUp(requestDto);
        log.info("dto email: {}", signUpResponse.getEmail());
        assertEquals(email, signUpResponse.getEmail());
    }

    @DisplayName("닉네임이 중복될 경우 AlreadyExitsNicknameException이 발생한다.")
    @Test
    void alreadyExistsNicknameThrowTest() {
        UserDto.AccountUpdateRequest request = new UserDto.AccountUpdateRequest("test", UserPersonalInformation.Gender.MALE, 25);
        when(this.userRepository.existsByNickname(any())).thenReturn(true);
        assertThrows(AlreadyExistsNicknameException.class, () -> this.userService.updateAccount(request));
        verify(this.userRepository, atMostOnce()).existsByNickname(any());
    }

    private SignUpRequest createSignUpRequest(String email, String password) {
        return new SignUpRequest(email, password, "HB", UserPersonalInformation.Gender.FEMALE, 20, 160, 50, Collections.emptySet());
    }

    @DisplayName("닉네임, 성별, 나이 변경에 성공한다.")
    @Test
    void updateAccountTest(){
        User user = User.builder()
                .nickname("test")
                .gender(UserPersonalInformation.Gender.FEMALE)
                .age(20).build();
        String nickname = "nickname is updated!";
        UserPersonalInformation.Gender gender = UserPersonalInformation.Gender.MALE;
        Integer age = 25;
        UserDto.AccountUpdateRequest request = new UserDto.AccountUpdateRequest(nickname, gender, age);

        try (MockedStatic<SecurityUtils> securityUtilsMocked = mockStatic(SecurityUtils.class)){
            securityUtilsMocked.when((SecurityUtils::getLoggedInUser)).thenReturn(user);
            when(this.userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
            this.userService.updateAccount(request);
            assertEquals(nickname, user.getNickname());
            assertEquals(gender, user.getPersonalInformation().getGender());
            assertEquals(age, user.getPersonalInformation().getAge());
        }
    }

    @DisplayName("키, 몸무게 변경에 성공한다.")
    @Test
    void updateProfileTest(){
        User user = User.builder()
                .height(100)
                .weight(50).build();
        Integer height = 200;
        Integer weight = 100;
        UserDto.ProfileUpdateRequest request = new UserDto.ProfileUpdateRequest(height, weight);

        try(MockedStatic<SecurityUtils> securityUtilsMocked = mockStatic(SecurityUtils.class)){
            securityUtilsMocked.when(SecurityUtils::getLoggedInUser).thenReturn(user);
            when(this.userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
            this.userService.updateProfile(request);
            assertEquals(height, user.getPersonalInformation().getHeight());
            assertEquals(weight, user.getPersonalInformation().getWeight());
        }
    }

    @DisplayName("회원 정보 조회에 성공한다.")
    @Test
    void getUserTest() {
        Email email = new Email("test@test.com");
        String nickname = "test";
        UserPersonalInformation.Gender gender = UserPersonalInformation.Gender.MALE;
        Integer age = 20;
        Integer height = 200;
        Integer weight = 100;
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .gender(gender)
                .age(age)
                .height(height)
                .weight(weight)
                .build();
        UserDto.GetUserResponse response = new UserDto.GetUserResponse(email.getValue(), nickname, gender, age, height, weight);

        try (MockedStatic<SecurityUtils> securityUtilsMocked = mockStatic(SecurityUtils.class)) {
            securityUtilsMocked.when(SecurityUtils::getLoggedInUser).thenReturn(user);
            UserDto.GetUserResponse getUserResponse = this.userService.getUser();
            assertThat(getUserResponse).usingRecursiveComparison().isEqualTo(response);
        }
    }
}