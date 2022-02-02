package com.oclothes.domain.user.dto;

import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.domain.User.Status;
import com.oclothes.domain.user.domain.User.UserBuilder;
import com.oclothes.domain.user.dto.UserDto.DefaultResponse;
import com.oclothes.domain.user.dto.UserDto.SignUpRequest;
import com.oclothes.global.config.security.PasswordEncoderConfig;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-02T16:19:33+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public User toEntity(SignUpRequest request) {
        if ( request == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.password( passwordEncoderConfig.encode( request.getPassword() ) );

        user.email( new Email(request.getEmail()) );
        user.role( User.Role.ROLE_USER );
        user.status( User.Status.WAIT );

        return user.build();
    }

    @Override
    public User toEntity(SignUpRequest request, Status status) {
        if ( request == null && status == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        if ( request != null ) {
            user.password( passwordEncoderConfig.encode( request.getPassword() ) );
        }
        if ( status != null ) {
            user.status( status );
        }
        user.email( new Email(request.getEmail()) );
        user.role( User.Role.ROLE_USER );

        return user.build();
    }

    @Override
    public DefaultResponse entityToDefaultResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        Character gender = null;
        Integer age = null;
        Integer height = null;
        Integer weight = null;

        id = user.getId();
        gender = user.getGender();
        age = user.getAge();
        height = user.getHeight();
        weight = user.getWeight();

        DefaultResponse defaultResponse = new DefaultResponse( id, gender, age, height, weight );

        return defaultResponse;
    }
}
