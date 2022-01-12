package com.oclothes.domain.user.service;

import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.exception.AlreadyExistsEmailException;
import com.oclothes.domain.user.exception.UserExceptionMessage;
import com.oclothes.domain.user.exception.UserStatusIsWaitException;
import com.oclothes.infra.email.domain.EmailAuthenticationCode;
import com.oclothes.infra.email.service.EmailAuthenticationCodeService;
import com.oclothes.infra.email.service.EmailService;
import com.oclothes.infra.email.util.EmailAuthenticationCodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//@ContextConfiguration(classes = {PasswordEncoderConfig.class})
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
        User user = this.createUser(requestDto);
        when(this.userRepository.existsByEmail_Value(any())).thenReturn(true);
        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        assertThrows(UserStatusIsWaitException.class, () ->
                        this.userService.signUp(requestDto),
                UserExceptionMessage.ALREADY_EXISTS_EMAIL.getMessage());
        verify(this.userRepository, atMostOnce()).existsByEmail_Value(any());
    }

    @DisplayName("이미 회원가입이 된 이메일은 AlreadyExistsEmailException 엑셉션이 발생한다.")
    @Test
    void alreadyExistsEmailThrowTest() {
        SignUpRequest requestDto = new SignUpRequest("email@gmail.com", "123456");
        User user = this.createUser(requestDto);
        user.setStatus(User.Status.NORMAL);
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
        User user = this.createUser(requestDto);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.emailAuthenticationCodeService.save(any())).thenReturn(new EmailAuthenticationCode(user, EmailAuthenticationCodeGenerator.generateAuthCode()));
        doNothing().when(this.emailService).sendEmail(any(), any(), any());
        SignUpResponseDto signUpResponseDto = this.userService.signUp(requestDto);
        log.info(() -> String.format("dto email: %s", signUpResponseDto.getEmail()));
        assertEquals(email, signUpResponseDto.getEmail());
    }

    private User createUser(SignUpRequest requestDto) {
        return User.builder()
                .status(User.Status.WAIT)
                .role(User.Role.USER)
                .email(new Email(requestDto.getEmail()))
                .password(this.passwordEncoder.encode(requestDto.getPassword()))
                .build();
    }
}