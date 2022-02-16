package com.oclothes.domain.clothes.dto;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "seasonTags", source = "clothes.seasonTags", qualifiedByName = "mapToTagDto")
@Mapping(target = "eventTags", source = "clothes.eventTags", qualifiedByName = "mapToTagDto")
@Mapping(target = "moodTags", source = "clothes.moodTags", qualifiedByName = "mapToTagDto")
public @interface MapToTagDto {
}
