package com.oclothes.domain.user.service;

import com.oclothes.domain.user.dto.UserDto;

public interface UserService {
    UserDto.SignUpResponseDto signUp(UserDto.SignUpRequest requestDto);
}
