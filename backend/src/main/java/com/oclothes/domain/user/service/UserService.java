package com.oclothes.domain.user.service;

import com.oclothes.domain.user.domain.User;

import static com.oclothes.domain.user.dto.UserDto.*;

public interface UserService {
    SignUpResponse signUp(SignUpRequest requestDto);

    LoginResponse login(LoginRequest loginRequest);

    User findByEmail(String email);

    void updateAccount(AccountUpdateRequest request);

    void updateProfile(ProfileUpdateRequest request);

    GetUserResponse getUser();
}
