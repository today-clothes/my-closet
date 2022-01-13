package com.oclothes.domain.user.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.exception.*;
import com.oclothes.global.error.exception.UserStatusException;
import com.oclothes.infra.email.domain.EmailAuthenticationCode;
import com.oclothes.infra.email.service.EmailAuthenticationCodeService;
import com.oclothes.infra.email.service.EmailService;
import com.oclothes.infra.email.util.EmailAuthenticationCodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest extends BaseTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

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
        User user = this.createUser(requestDto, User.Status.WAIT);
        when(this.userRepository.existsByEmail_Value(any())).thenReturn(true);
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(UserStatusIsWaitException.class, () -> this.userService.signUp(requestDto));
        verify(this.userRepository, atMostOnce()).existsByEmail_Value(any());
    }

    @DisplayName("이미 회원가입이 된 이메일은 AlreadyExistsEmailException이 발생한다.")
    @Test
    void alreadyExistsEmailThrowTest() {
        SignUpRequest requestDto = new SignUpRequest("email@gmail.com", "123456");
        User user = this.createUser(requestDto, User.Status.NORMAL);
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
        User user = this.createUser(requestDto, User.Status.WAIT);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.emailAuthenticationCodeService.save(any())).thenReturn(new EmailAuthenticationCode(user, EmailAuthenticationCodeGenerator.generateAuthCode()));
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
        User user = this.createUser(new SignUpRequest(email, "123456"), User.Status.NORMAL);
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(UserStatusException.class, () -> this.userService.emailAuthentication(email, "ABCDEFG"));
    }

    @DisplayName("인증 코드가 틀릴 경우 WrongEmailAuthenticationCodeException이 발생한다.")
    @Test
    void wrongEmailAuthenticationCodeExceptionTest() {
        String email = "test@gmail.com";
        String authCode = EmailAuthenticationCodeGenerator.generateAuthCode();
        User user = this.createUser(new SignUpRequest(email, "123456"), User.Status.WAIT);
        user.setEmailAuthenticationCode(new EmailAuthenticationCode(user, authCode));
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(WrongEmailAuthenticationCodeException.class, () -> this.userService.emailAuthentication(email, "ABCDEFG"));
    }

    @DisplayName("이메일 인증에 성공한다.")
    @Test
    void successEmailAuthentication() {
        String email = "test@gmail.com";
        String authCode = EmailAuthenticationCodeGenerator.generateAuthCode();
        User user = this.createUser(new SignUpRequest(email, "123456"), User.Status.WAIT);
        user.setEmailAuthenticationCode(new EmailAuthenticationCode(user, authCode));
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        SignUpResponse signUpResponse = this.userService.emailAuthentication(email, authCode);
        assertNotNull(signUpResponse);
        assertEquals(email, signUpResponse.getEmail());
        assertEquals(User.Status.NORMAL, user.getStatus());
    }

    private User createUser(SignUpRequest requestDto, User.Status status) {
        return User.builder()
                .status(status)
                .role(User.Role.ROLE_USER)
                .email(new Email(requestDto.getEmail()))
                .password(this.passwordEncoder.encode(requestDto.getPassword()))
                .build();
    }
}