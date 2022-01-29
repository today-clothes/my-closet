package com.oclothes.domain.closet.dto;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.closet.domain.Closet.ClosetBuilder;
import com.oclothes.domain.closet.dto.ClosetDto.CreateRequest;
import com.oclothes.domain.closet.dto.ClosetDto.CreateResponse;
import com.oclothes.domain.closet.dto.ClosetDto.DefaultResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-28T17:29:35+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ClosetMapperImpl implements ClosetMapper {

    @Override
    public Closet toEntity(CreateRequest request) {
        if ( request == null ) {
            return null;
        }

        ClosetBuilder closet = Closet.builder();

        closet.name( request.getName() );
        if ( request.getLocked() != null ) {
            closet.locked( request.getLocked() );
        }

        closet.user( com.oclothes.global.config.security.util.SecurityUtils.getLoggedInUser() );

        return closet.build();
    }

    @Override
    public DefaultResponse entityToDefaultResponse(Closet closet) {
        if ( closet == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        boolean locked = false;

        id = closet.getId();
        name = closet.getName();
        locked = closet.isLocked();

        DefaultResponse defaultResponse = new DefaultResponse( id, name, locked );

        return defaultResponse;
    }

    @Override
    public CreateResponse entityToCreateResponse(Closet closet) {
        if ( closet == null ) {
            return null;
        }

        Long id = null;
        boolean locked = false;
        String name = null;

        id = closet.getId();
        locked = closet.isLocked();
        name = closet.getName();

        CreateResponse createResponse = new CreateResponse( id, locked, name );

        return createResponse;
    }
}
