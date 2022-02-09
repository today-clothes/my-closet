package com.oclothes.domain.user.controller;

import com.oclothes.domain.user.service.EmailAuthenticationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/email-auth")
public class EmailAuthenticationController {
    private final EmailAuthenticationCodeService emailAuthenticationCodeService;

    @GetMapping
    public String emailAuthentication(@RequestParam String email, @RequestParam String code) {
        this.emailAuthenticationCodeService.emailAuthentication(email, code);
        return "sign-up-success";
    }
}
