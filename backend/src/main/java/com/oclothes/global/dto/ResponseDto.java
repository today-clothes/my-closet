package com.oclothes.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oclothes.domain.clothes.domain.Clothes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private String message;
    private T data;

    public static <T> ResponseDto<T> create(String message) {
        return new ResponseDto<>(message, null);
    }

    public static <T> ResponseDto<T> create(String message, T dto) {
        return new ResponseDto<>(message, dto);
    }

}
