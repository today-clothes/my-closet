package com.oclothes.domain.closet.dto;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.global.config.security.util.SecurityUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClosetMapperSupport.class}, imports = SecurityUtils.class)
public interface ClosetMapper {
    @Mapping(target = "user", expression = "java(SecurityUtils.getLoggedInUser())")
    Closet toEntity(ClosetDto.CreateRequest request);

    @Mapping(target = "thumbnail", source = "closet", qualifiedByName = "mapThumbnail")
    ClosetDto.DefaultResponse entityToDefaultResponse(Closet closet);
}
