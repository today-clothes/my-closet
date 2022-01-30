package com.oclothes.domain.user.controller;

import com.oclothes.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/email-auth/{email}/{code}")
    public String emailAuthentication(@PathVariable String email, @PathVariable String code) {
        this.userService.emailAuthentication(email, code);
        return "sign-up-success";
    }

}
