package com.oclothes.domain.user.api;

import com.oclothes.domain.user.dto.UserResponseMessage;
import com.oclothes.domain.user.service.UserService;
import com.oclothes.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.oclothes.domain.user.dto.UserDto.*;
import static com.oclothes.domain.user.dto.UserResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserApiController {
    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입 API")
    @PostMapping
    public ResponseEntity<ResponseDto<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest requestDto) {
        return ResponseEntity.status(CREATED).body(ResponseDto.create(UserResponseMessage.SIGN_UP_SUCCESS.getMessage(), this.userService.signUp(requestDto)));
    }

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(ResponseDto.create(UserResponseMessage.LOGIN_SUCCESS.getMessage(), this.userService.login(loginRequest)));
    }

    @ApiOperation(value = "닉네임, 성별, 나이 업데이트", notes = "닉네임, 성별, 나이 업데이트 API")
    @PatchMapping("/my-account")
    public ResponseEntity<ResponseDto<String>> updateAccount(@Valid @RequestBody AccountUpdateRequest request){
        this.userService.updateAccount(request);
        return ResponseEntity.ok(ResponseDto.create(UPDATE_USER_ACCOUNT_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "키, 몸무게 업데이트", notes = "키, 몸무게 업데이트 API")
    @PatchMapping("/my-profile")
    public ResponseEntity<ResponseDto<String>> updateProfile(@Valid @RequestBody ProfileUpdateRequest request){
        this.userService.updateProfile(request);
        return ResponseEntity.ok(ResponseDto.create(UPDATE_USER_PROFILE_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보 조회 API")
    @GetMapping
    public ResponseEntity<ResponseDto<GetUserResponse>> getUser(){
        return ResponseEntity.ok(ResponseDto.create(GET_USER_SUCCESS.getMessage(), this.userService.getUser()));
    }
}