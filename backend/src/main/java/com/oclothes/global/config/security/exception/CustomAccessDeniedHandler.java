package com.oclothes.global.config.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclothes.global.error.dto.ExceptionResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String EXCEPTION_MESSAGE = "잘못된 접근입니다.";
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(objectMapper.writeValueAsString(ExceptionResponse.create(EXCEPTION_MESSAGE)));
    }
}
