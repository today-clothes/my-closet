package com.oclothes.domain.user.dto;

import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.PasswordEncoderConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.oclothes.domain.user.dto.UserDto.*;

@Mapper(componentModel = "spring", uses = PasswordEncoderConfig.class, imports = {Email.class})
public interface UserMapper {
    @Mapping(target = "email", expression = "java(new Email(request.getEmail()))")
    @Mapping(target = "password", source = "password", qualifiedByName = "passwordEncode")
    @Mapping(target = "role", expression = "java(User.Role.ROLE_USER)")
    @Mapping(target = "status", expression = "java(User.Status.WAIT)")
    User toEntity(SignUpRequest request);

    @Mapping(target = "email", expression = "java(new Email(request.getEmail()))")
    @Mapping(target = "password", source = "request.password",  qualifiedByName = "passwordEncode")
    @Mapping(target = "role", expression = "java(User.Role.ROLE_USER)")
    User toEntity(SignUpRequest request, User.Status status);

    @Mapping(target = "email", source = "user.email.value")
    SignUpResponse toSignUpResponse(User user);

    @Mapping(target = "email", expression = "java(user.getEmail().getValue())")
    @Mapping(target = "age", source = "user.personalInformation.age")
    @Mapping(target = "gender", source = "user.personalInformation.gender")
    @Mapping(target = "height", source = "user.personalInformation.height")
    @Mapping(target = "weight", source = "user.personalInformation.weight")
    GetUserResponse entityToGetUserResponse(User user);
}
