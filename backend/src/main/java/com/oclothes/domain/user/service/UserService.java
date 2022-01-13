package com.oclothes.domain.user.service;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponseDto;

public interface UserService {
    SignUpResponseDto signUp(SignUpRequest requestDto);

    SignUpResponseDto emailAuthentication(String email, String code);
}
