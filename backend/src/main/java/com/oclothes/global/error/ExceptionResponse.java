package com.oclothes.global.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private String errorMessage;
    private Map<String, String> errors;

    public static ExceptionResponse create(String errorMessage) {
        return new ExceptionResponse(errorMessage, null);
    }

    public static ExceptionResponse create(String errorMessage, Map<String, String> errors) {
        return new ExceptionResponse(errorMessage, errors);
    }
}
