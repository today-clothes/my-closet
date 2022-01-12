package com.oclothes.domain.user.api;

import com.oclothes.domain.user.service.UserService;
import com.oclothes.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import static com.oclothes.domain.user.dto.UserDto.SignUpResponseDto;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signUp(@Valid  @RequestBody SignUpRequest requestDto) {
        return ResponseEntity.status(CREATED).body(ResponseDto.create(this.userService.signUp(requestDto), "회원가입 성공."));
    }

}
