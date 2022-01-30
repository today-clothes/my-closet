package com.oclothes.domain.user.api;

import com.oclothes.domain.user.dto.UserDto;
import com.oclothes.domain.user.dto.UserResponseMessage;
import com.oclothes.domain.user.service.UserService;
import com.oclothes.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입 API")
    @PostMapping
    public ResponseEntity<ResponseDto<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest requestDto) {
        return ResponseEntity.status(CREATED).body(ResponseDto.create(UserResponseMessage.SIGN_UP.getMessage(), this.userService.signUp(requestDto)));
    }

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<UserDto.LoginResponse>> login(@Valid @RequestBody UserDto.LoginRequest loginRequest) {
        return ResponseEntity.ok(ResponseDto.create(UserResponseMessage.LOGIN.getMessage(), this.userService.login(loginRequest)));
    }
}
