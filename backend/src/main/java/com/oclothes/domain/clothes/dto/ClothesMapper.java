package com.oclothes.domain.clothes.dto;

import com.oclothes.domain.clothes.domain.Clothes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadRequest;
import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadResponse;

@Mapper(componentModel = "spring", uses = ClothesMapperSupport.class)
public interface ClothesMapper {

    @Mapping(target = "closet", source = "closetId", qualifiedByName = "findByClosetId")
    @Mapping(target = "imgUrl", source = "file", qualifiedByName = "saveImage")
    Clothes toEntity(ClothesUploadRequest request);

    @Mapping(target = "clothesId", source = "clothes.id")
    @Mapping(target = "closetId", source = "clothes.closet.id")
    ClothesUploadResponse toUploadResponse(Clothes clothes);

    @Mapping(target = "closetId", source = "clothes.closet.id")
    @Mapping(target = "clothesId", source = "clothes.id")
    ClothesDto.SearchResponse toSearchResponse(Clothes clothes);

    @Mapping(target = "clothesId", source = "clothes.id")
    ClothesDto.DefaultResponse toDefaultResponse(Clothes clothes);
}
