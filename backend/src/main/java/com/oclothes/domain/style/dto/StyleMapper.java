package com.oclothes.domain.style.dto;

import com.oclothes.domain.clothes.domain.ClothesStyle;
import com.oclothes.domain.style.domain.Style;
import org.mapstruct.Mapper;

import java.util.List;

import static com.oclothes.domain.style.dto.StyleDto.Response;

@Mapper(componentModel = "spring")
public interface StyleMapper {
    Response toResponse(Style style);

    List<Response> toResponse(List<Style> styles);

    List<ClothesStyle> toClothesStyle(List<Style> styles);
}
