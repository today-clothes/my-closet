package com.oclothes.domain.user.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.user.dao.EmailAuthenticationCodeRepository;
import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.EmailAuthenticationCode;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.domain.UserPersonalInformation;
import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.domain.user.dto.UserMapper;
import com.oclothes.domain.user.exception.AlreadyEmailAuthenticationException;
import com.oclothes.domain.user.exception.UserNotFoundException;
import com.oclothes.domain.user.exception.WrongEmailAuthenticationCodeException;
import com.oclothes.global.config.security.PasswordEncoderConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EmailAuthenticationCodeServiceImplTest extends BaseTest {

    @Spy
    PasswordEncoderConfig passwordEncoderConfig;
    @Spy
    @InjectMocks
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Mock
    private EmailAuthenticationCodeRepository repository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EmailAuthenticationCodeServiceImpl emailAuthenticationCodeService;

    @DisplayName("이메일 인증 시도 중 해당 유저가 존재하지 않으면 UserNotFoundException이 발생한다.")
    @Test
    void emailAuthenticationFailToUserNotFoundExceptionTest() {
        assertThrows(UserNotFoundException.class, () ->
                this.emailAuthenticationCodeService.emailAuthentication("no@gmail.com", ""));
    }

    @DisplayName("해당 이메일이 이미 인증된 이메일일 경우 UserStatusException이 발생한다.")
    @Test
    void userStatusIsAlreadyNormalExceptionTest() {
        String email = "test@gmail.com";
        User user = this.userMapper.toEntity(this.createSignUpRequest(email, "123456"), User.Status.NORMAL);
        final EmailAuthenticationCode emailAuthenticationCode = new EmailAuthenticationCode(user, "");

        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        when(this.repository.findByUser(any())).thenReturn(Optional.of(emailAuthenticationCode));

        assertThrows(AlreadyEmailAuthenticationException.class, () ->
                this.emailAuthenticationCodeService.emailAuthentication(email, ""));
    }

    @DisplayName("인증 코드가 틀릴 경우 WrongEmailAuthenticationCodeException이 발생한다.")
    @Test
    void wrongEmailAuthenticationCodeExceptionTest() {
        String email = "test@gmail.com";
        User user = this.userMapper.toEntity(this.createSignUpRequest(email, "123456"), User.Status.WAIT);
        final EmailAuthenticationCode emailAuthenticationCode = new EmailAuthenticationCode(user, "abcdefg");

        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        when(this.repository.findByUser(any())).thenReturn(Optional.of(emailAuthenticationCode));

        assertThrows(WrongEmailAuthenticationCodeException.class, () ->
                this.emailAuthenticationCodeService.emailAuthentication(email, "wrong"));
    }

    @DisplayName("이메일 인증에 성공한다.")
    @Test
    void successEmailAuthentication() {
        String email = "test@gmail.com";
        String authCode = "abcdefg";
        User user = this.userMapper.toEntity(this.createSignUpRequest(email, "123456"));
        final EmailAuthenticationCode emailAuthenticationCode = new EmailAuthenticationCode(user, authCode);

        when(this.userRepository.findByEmail_Value(any())).thenReturn(Optional.of(user));
        when(this.repository.findByUser(any())).thenReturn(Optional.of(emailAuthenticationCode));

        final UserDto.SignUpResponse result = this.emailAuthenticationCodeService.emailAuthentication(email, authCode);
        assertEquals(email, result.getEmail());
    }

    private UserDto.SignUpRequest createSignUpRequest(String email, String password) {
        return new UserDto.SignUpRequest(email, password, "HB", UserPersonalInformation.Gender.FEMALE, 20, 160, 50, Collections.emptySet());
    }

}