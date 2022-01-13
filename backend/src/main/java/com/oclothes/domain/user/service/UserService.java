package com.oclothes.domain.user.service;

import com.oclothes.domain.user.domain.User;

import static com.oclothes.domain.user.dto.UserDto.*;

public interface UserService {
    SignUpResponse signUp(SignUpRequest requestDto);

    SignUpResponse emailAuthentication(String email, String code);

    LoginResponse login(LoginRequest loginRequest);

    User findByEmail(String email);
}
