package com.oclothes.domain.user.service;

import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.EmailAuthenticationCode;
import com.oclothes.domain.user.domain.EmailSubject;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.domain.user.dto.UserMapper;
import com.oclothes.domain.user.exception.AlreadyExistsEmailException;
import com.oclothes.domain.user.exception.UserNotFoundException;
import com.oclothes.domain.user.exception.UserStatusIsWaitException;
import com.oclothes.domain.user.util.EmailAuthenticationCodeGenerator;
import com.oclothes.domain.user.util.EmailMessageUtil;
import com.oclothes.global.config.security.jwt.JwtProvider;
import com.oclothes.infra.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EmailAuthenticationCodeService emailAuthenticationCodeService;
    private final EmailService emailService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Override
    public SignUpResponse signUp(SignUpRequest requestDto) {
        this.validateAlreadyExistsEmail(requestDto);
        User savedUser = this.userRepository.save(this.userMapper.toEntity(requestDto));
        String authenticationCode = EmailAuthenticationCodeGenerator.generateAuthCode();
        savedUser.setEmailAuthenticationCode(this.emailAuthenticationCodeService.save(new EmailAuthenticationCode(authenticationCode)));
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

    @Override
    public UserDto.DefaultResponse updateProfile(Long id, UserDto.ProfileUpdateRequest request){
        final User user = this.findById(id);
        user.setGender(request.getGender());
        user.setAge(request.getAge());
        user.setHeight(request.getHeight());
        user.setWeight(request.getWeight());
        return userMapper.entityToDefaultResponse(user);
    }

    public User findById(Long id){
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
