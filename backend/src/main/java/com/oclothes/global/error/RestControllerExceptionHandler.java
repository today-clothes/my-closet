package com.oclothes.global.error;

import com.oclothes.global.error.dto.ExceptionResponse;
import com.oclothes.global.error.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler {

    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> validationExceptionHandle(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(ExceptionResponse.create(ExceptionMessage.INVALID_REQUEST.getMessage(),
                exception.getBindingResult().getFieldErrors().stream()
                        .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))));
    }

    @ExceptionHandler({AlreadyExistsException.class, UserStatusException.class, NotFoundException.class})
    public ResponseEntity<ExceptionResponse> alreadyExistsExceptionHandle(Exception exception) {
        return ResponseEntity.badRequest().body(ExceptionResponse.create(exception.getMessage()));
    }

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<ExceptionResponse> tooManyRequestExceptionHandle(Exception exception) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ExceptionResponse.create(exception.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> expiredJwtExceptionHandle(Exception exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponse.create(exception.getMessage()));
    }

}
