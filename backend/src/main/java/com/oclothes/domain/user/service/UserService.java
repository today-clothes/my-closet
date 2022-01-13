package com.oclothes.domain.user.service;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;

public interface UserService {
    SignUpResponse signUp(SignUpRequest requestDto);

    SignUpResponse emailAuthentication(String email, String code);
}
