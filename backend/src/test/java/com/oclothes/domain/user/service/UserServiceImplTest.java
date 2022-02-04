package com.oclothes.domain.user.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.EmailAuthenticationCode;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.domain.user.dto.UserMapper;
import com.oclothes.domain.user.exception.*;
import com.oclothes.domain.user.util.EmailAuthenticationCodeGenerator;
import com.oclothes.global.config.security.PasswordEncoderConfig;
import com.oclothes.global.error.exception.UserStatusException;
import com.oclothes.infra.email.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Optional;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.junit.jupiter.api.Assertions.*;
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

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @DisplayName("회원가입 대기 상태인 유저는 UserStatusIsWaitException 엑셉션이 발생한다.")
    @Test
    void userStateIsWaitExceptionTest() {
        SignUpRequest requestDto = new SignUpRequest("email@gmail.com", "123456");
        User user = this.userMapper.toEntity(requestDto);
        when(this.userRepository.existsByEmail_Value(any())).thenReturn(true);
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(UserStatusIsWaitException.class, () -> this.userService.signUp(requestDto));
        verify(this.userRepository, atMostOnce()).existsByEmail_Value(any());
    }

    @DisplayName("이미 회원가입이 된 이메일은 AlreadyExistsEmailException이 발생한다.")
    @Test
    void alreadyExistsEmailThrowTest() {
        SignUpRequest requestDto = new SignUpRequest("email@gmail.com", "123456");
        User user = this.userMapper.toEntity(requestDto, User.Status.NORMAL);
        when(this.userRepository.existsByEmail_Value(any())).thenReturn(true);
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(AlreadyExistsEmailException.class, () ->
                        this.userService.signUp(requestDto),
                UserExceptionMessage.ALREADY_EXISTS_EMAIL.getMessage());
        verify(this.userRepository, atMostOnce()).existsByEmail_Value(any());
    }

    @DisplayName("유저가 회원가입 인증메일을 요청에 성공한다.")
    @Test
    void signUpTest() {
        String email = "test@gmail.com";
        SignUpRequest requestDto = new SignUpRequest(email, "123456");
        User user = this.userMapper.toEntity(requestDto, User.Status.WAIT);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.emailAuthenticationCodeService.save(any())).thenReturn(new EmailAuthenticationCode(EmailAuthenticationCodeGenerator.generateAuthCode()));
        doNothing().when(this.emailService).sendEmail(any(), any(), any());
        SignUpResponse signUpResponse = this.userService.signUp(requestDto);
        log.info("dto email: {}", signUpResponse.getEmail());
        assertEquals(email, signUpResponse.getEmail());
    }

    @DisplayName("이메일 인증 시도 중 해당 유저가 존재하지 않으면 UserNotFoundException이 발생한다.")
    @Test
    void emailAuthenticationFailToUserNotFoundExceptionTest() {
        assertThrows(UserNotFoundException.class, () -> this.userService.emailAuthentication("no@gmail.com", "ABCDEFG"));
    }

    @DisplayName("해당 이메일이 이미 인증된 이메일일 경우 UserStatusException이 발생한다.")
    @Test
    void userStatusIsAlreadyNormalExceptionTest() {
        String email = "test@gmail.com";
        User user = this.userMapper.toEntity(new SignUpRequest(email, "123456"), User.Status.NORMAL);
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(UserStatusException.class, () -> this.userService.emailAuthentication(email, "ABCDEFG"));
    }

    @DisplayName("인증 코드가 틀릴 경우 WrongEmailAuthenticationCodeException이 발생한다.")
    @Test
    void wrongEmailAuthenticationCodeExceptionTest() {
        String email = "test@gmail.com";
        String authCode = EmailAuthenticationCodeGenerator.generateAuthCode();
        User user = this.userMapper.toEntity((new SignUpRequest(email, "123456")), User.Status.WAIT);
        user.setEmailAuthenticationCode(new EmailAuthenticationCode(authCode));
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(WrongEmailAuthenticationCodeException.class, () -> this.userService.emailAuthentication(email, "ABCDEFG"));
    }

    @DisplayName("이메일 인증에 성공한다.")
    @Test
    void successEmailAuthentication() {
        String email = "test@gmail.com";
        String authCode = EmailAuthenticationCodeGenerator.generateAuthCode();
        User user = this.userMapper.toEntity(new SignUpRequest(email, "123456"));
        user.setEmailAuthenticationCode(new EmailAuthenticationCode(authCode));
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        SignUpResponse signUpResponse = this.userService.emailAuthentication(email, authCode);
        assertNotNull(signUpResponse);
        assertEquals(email, signUpResponse.getEmail());
        assertEquals(User.Status.NORMAL, user.getStatus());
    }

    @DisplayName("회원 프로필 변경에 성공한다.")
    @Test
    void updateProfileTest() {
        final Long id = 1L;
        final User.Gender gender = User.Gender.MALE;
        final Integer age = 25;
        final Integer height = 200;
        final Integer weight = 100;
        final User user = User.builder()
                .gender(User.Gender.FEMALE)
                .age(1)
                .height(1)
                .weight(1)
                .build();
        final UserDto.ProfileUpdateRequest request = new UserDto.ProfileUpdateRequest(id, gender, age, height, weight);

        when(this.userRepository.findById(any())).thenReturn(Optional.of(user));

        final UserDto.DefaultResponse response = this.userService.updateProfile(id, request);
        assertEquals(gender, response.getGender());
        assertEquals(age, response.getAge());
        assertEquals(height, response.getHeight());
        assertEquals(weight, response.getWeight());
    }
}