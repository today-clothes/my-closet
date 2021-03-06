package com.oclothes.global.error;

import com.oclothes.domain.user.controller.EmailAuthenticationController;
import com.oclothes.domain.user.exception.UserNotFoundException;
import com.oclothes.domain.user.exception.WrongEmailAuthenticationCodeException;
import com.oclothes.global.error.exception.UserStatusException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackageClasses = {EmailAuthenticationController.class})
public class ControllerExceptionHandler {

    @ExceptionHandler({UserStatusException.class, WrongEmailAuthenticationCodeException.class})
    public ModelAndView emailAuthenticationExceptionHandle(Exception e) {
        ModelAndView modelAndView = new ModelAndView("sign-up-fail");
        modelAndView.addObject("exception", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler({UserNotFoundException.class})
    public String return404Page(Exception e) {
        return "404";
    }

}
