package com.oclothes.domain.user.dto;

import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.PasswordEncoderConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderConfig.class, imports = {Email.class})
public interface UserMapper {

    @Mapping(target = "email", expression = "java(new Email(request.getEmail()))")
    @Mapping(target = "password", source = "password", qualifiedByName = "passwordEncode")
    @Mapping(target = "role", expression = "java(User.Role.ROLE_USER)")
    @Mapping(target = "status", expression = "java(User.Status.WAIT)")
    User toEntity(UserDto.SignUpRequest request);

    @Mapping(target = "email", expression = "java(new Email(request.getEmail()))")
    @Mapping(target = "password", source = "request.password",  qualifiedByName = "passwordEncode")
    @Mapping(target = "role", expression = "java(User.Role.ROLE_USER)")
    User toEntity(UserDto.SignUpRequest request, User.Status status);

    UserDto.DefaultResponse entityToDefaultResponse(User user);
}
