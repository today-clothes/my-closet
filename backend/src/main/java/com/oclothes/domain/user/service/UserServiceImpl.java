package com.oclothes.domain.user.service;

import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.tag.service.TagService;
import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.EmailAuthenticationCode;
import com.oclothes.domain.user.domain.EmailSubject;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.domain.user.dto.UserMapper;
import com.oclothes.domain.user.exception.AlreadyExistsEmailException;
import com.oclothes.domain.user.exception.AlreadyExistsNicknameException;
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

import java.util.List;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static com.oclothes.global.config.security.util.SecurityUtils.getLoggedInUser;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EmailAuthenticationCodeService emailAuthenticationCodeService;
    private final EmailService emailService;
    private final TagService tagService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Override
    public SignUpResponse signUp(SignUpRequest requestDto) {
        this.validateAlreadyExistsEmail(requestDto);
        this.validateAlreadyExistsNickname(requestDto.getNickname());
        final List<MoodTag> moodTags = this.tagService.findAllByMoodTagIds(requestDto.getMoodTags());
        User user = this.userRepository.save(this.userMapper.toEntity(requestDto))
                .addAllMoodTags(moodTags);
        String authenticationCode = EmailAuthenticationCodeGenerator.generateAuthCode();
        this.emailAuthenticationCodeService.save(new EmailAuthenticationCode(user, authenticationCode));
        this.emailService.sendEmail(user.getEmail(), EmailSubject.SIGN_UP, EmailMessageUtil.getSignUpEmailMessage(user.getEmail(), authenticationCode));
        return new SignUpResponse(user.getEmail().getValue());
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

    private User findById(Long id){
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void updateAccount(UserDto.AccountUpdateRequest request){
        this.validateAlreadyExistsNickname(request.getNickname());
        this.findById(getLoggedInUser().getId()).updateUserAccount(request);
    }

    @Override
    public void updateProfile(UserDto.ProfileUpdateRequest request){
        this.findById(getLoggedInUser().getId()).updateUserProfile(request);
    }

    private void validateAlreadyExistsNickname(String nickname){
        if (this.userRepository.existsByNickname(nickname)) throw new AlreadyExistsNicknameException();
    }

    @Override
    public UserDto.GetUserResponse getUser(){
        return this.userMapper.entityToGetUserResponse(getLoggedInUser());
    }
}
