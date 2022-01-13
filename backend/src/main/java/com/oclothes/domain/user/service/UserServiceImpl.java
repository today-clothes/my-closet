package com.oclothes.domain.user.service;

import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.domain.user.exception.AlreadyExistsEmailException;
import com.oclothes.domain.user.exception.UserNotFoundException;
import com.oclothes.domain.user.exception.UserStatusIsWaitException;
import com.oclothes.global.config.security.jwt.JwtProvider;
import com.oclothes.infra.email.domain.EmailAuthenticationCode;
import com.oclothes.infra.email.domain.EmailSubject;
import com.oclothes.infra.email.service.EmailAuthenticationCodeService;
import com.oclothes.infra.email.service.EmailService;
import com.oclothes.infra.email.util.EmailAuthenticationCodeGenerator;
import com.oclothes.infra.email.util.EmailMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthenticationCodeService emailAuthenticationCodeService;
    private final EmailService emailService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Override
    public SignUpResponse signUp(SignUpRequest requestDto) {
        this.validateAlreadyExistsEmail(requestDto);
        User savedUser = this.userRepository.save(createUser(requestDto));
        String authenticationCode = EmailAuthenticationCodeGenerator.generateAuthCode();
        savedUser.setEmailAuthenticationCode(this.emailAuthenticationCodeService.save(
                new EmailAuthenticationCode(savedUser, authenticationCode)));
        this.emailService.sendEmail(savedUser.getEmail(), EmailSubject.SIGN_UP, EmailMessageUtil.getSignUpEmailMessage(savedUser.getEmail(), authenticationCode));
        return new SignUpResponse(savedUser.getEmail().getValue());
    }

    @Override
    public SignUpResponse emailAuthentication(String email, String code) {
        return new SignUpResponse(this.findByEmail(email).emailAuthentication(code).getEmail().getValue());
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail_Value(email).orElseThrow(UserNotFoundException::new);
    }

    private User createUser(SignUpRequest requestDto) {
        return User.builder()
                .status(User.Status.WAIT)
                .role(User.Role.ROLE_USER)
                .email(new Email(requestDto.getEmail()))
                .password(this.passwordEncoder.encode(requestDto.getPassword()))
                .build();
    }

    private void validateAlreadyExistsEmail(SignUpRequest requestDto) {
        if (this.userRepository.existsByEmail_Value(requestDto.getEmail())) {
            User.Status status = this.findByEmail(requestDto.getEmail()).getStatus();
            if (status.equals(User.Status.NORMAL)) throw new AlreadyExistsEmailException();
            if (status.equals(User.Status.WAIT)) throw new UserStatusIsWaitException();
        }
    }

    @Override
    public UserDto.LoginResponse login(UserDto.LoginRequest loginRequest) {
        this.findByEmail(loginRequest.getEmail());
        return this.jwtProvider.generateToken(this.authenticationManagerBuilder.getObject()
                .authenticate(loginRequest.toAuthentication()));
    }

}
