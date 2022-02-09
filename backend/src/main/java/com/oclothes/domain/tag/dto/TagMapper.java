package com.oclothes.domain.tag.dto;

import com.oclothes.domain.tag.domain.Tag;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.oclothes.domain.tag.dto.TagDto.Response;

@Mapper(componentModel = "spring")
public interface TagMapper {
    default <T extends Tag> List<Response> toResponse(List<T> tags) {
        return tags.stream().map(tag -> new Response(tag.getId(), tag.getName())).collect(Collectors.toList());
    }
}
