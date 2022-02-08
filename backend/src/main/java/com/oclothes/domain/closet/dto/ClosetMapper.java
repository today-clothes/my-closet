package com.oclothes.domain.closet.dto;

import com.oclothes.domain.closet.domain.Closet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClosetMapper {
    @Mapping(target = "user", expression = "java(com.oclothes.global.config.security.util.SecurityUtils.getLoggedInUser())")
    Closet toEntity(ClosetDto.CreateRequest request);

    ClosetDto.DefaultResponse entityToDefaultResponse(Closet closet);
}
