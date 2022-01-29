package com.oclothes.domain.clothes.dto;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.domain.Clothes.ClothesBuilder;
import com.oclothes.domain.clothes.domain.ClothesEventTag;
import com.oclothes.domain.clothes.domain.ClothesMoodTag;
import com.oclothes.domain.clothes.domain.ClothesSeasonTag;
import com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadRequest;
import com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadResponse;
import com.oclothes.domain.clothes.dto.ClothesDto.SearchResponse;
import com.oclothes.domain.tag.dto.TagDto.Response;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-28T17:29:35+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ClothesMapperImpl implements ClothesMapper {

    @Autowired
    private ClothesMapperSupport clothesMapperSupport;

    @Override
    public Clothes toEntity(ClothesUploadRequest request) {
        if ( request == null ) {
            return null;
        }

        ClothesBuilder clothes = Clothes.builder();

        clothes.closet( clothesMapperSupport.findByClosetId( request.getClosetId() ) );
        try {
            clothes.imgUrl( clothesMapperSupport.saveImage( request.getFile() ) );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        return clothes.build();
    }

    @Override
    public ClothesUploadResponse toUploadResponse(Clothes clothes) {
        if ( clothes == null ) {
            return null;
        }

        Long clothesId = null;
        Long closetId = null;

        clothesId = clothes.getId();
        closetId = clothesClosetId( clothes );

        ClothesUploadResponse clothesUploadResponse = new ClothesUploadResponse( closetId, clothesId );

        return clothesUploadResponse;
    }

    @Override
    public SearchResponse toSearchResponse(Clothes clothes) {
        if ( clothes == null ) {
            return null;
        }

        Long closetId = null;
        Long clothesId = null;
        Set<Response> seasonTags = null;
        Set<Response> eventTags = null;
        Set<Response> moodTags = null;
        String imgUrl = null;

        closetId = clothesClosetId( clothes );
        clothesId = clothes.getId();
        seasonTags = clothesSeasonTagSetToResponseSet( clothes.getSeasonTags() );
        eventTags = clothesEventTagSetToResponseSet( clothes.getEventTags() );
        moodTags = clothesMoodTagSetToResponseSet( clothes.getMoodTags() );
        imgUrl = clothes.getImgUrl();

        SearchResponse searchResponse = new SearchResponse( closetId, clothesId, seasonTags, eventTags, moodTags, imgUrl );

        return searchResponse;
    }

    private Long clothesClosetId(Clothes clothes) {
        if ( clothes == null ) {
            return null;
        }
        Closet closet = clothes.getCloset();
        if ( closet == null ) {
            return null;
        }
        Long id = closet.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Response clothesSeasonTagToResponse(ClothesSeasonTag clothesSeasonTag) {
        if ( clothesSeasonTag == null ) {
            return null;
        }

        Long id = null;

        id = clothesSeasonTag.getId();

        String name = null;

        Response response = new Response( id, name );

        return response;
    }

    protected Set<Response> clothesSeasonTagSetToResponseSet(Set<ClothesSeasonTag> set) {
        if ( set == null ) {
            return null;
        }

        Set<Response> set1 = new HashSet<Response>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ClothesSeasonTag clothesSeasonTag : set ) {
            set1.add( clothesSeasonTagToResponse( clothesSeasonTag ) );
        }

        return set1;
    }

    protected Response clothesEventTagToResponse(ClothesEventTag clothesEventTag) {
        if ( clothesEventTag == null ) {
            return null;
        }

        Long id = null;

        id = clothesEventTag.getId();

        String name = null;

        Response response = new Response( id, name );

        return response;
    }

    protected Set<Response> clothesEventTagSetToResponseSet(Set<ClothesEventTag> set) {
        if ( set == null ) {
            return null;
        }

        Set<Response> set1 = new HashSet<Response>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ClothesEventTag clothesEventTag : set ) {
            set1.add( clothesEventTagToResponse( clothesEventTag ) );
        }

        return set1;
    }

    protected Response clothesMoodTagToResponse(ClothesMoodTag clothesMoodTag) {
        if ( clothesMoodTag == null ) {
            return null;
        }

        Long id = null;

        id = clothesMoodTag.getId();

        String name = null;

        Response response = new Response( id, name );

        return response;
    }

    protected Set<Response> clothesMoodTagSetToResponseSet(Set<ClothesMoodTag> set) {
        if ( set == null ) {
            return null;
        }

        Set<Response> set1 = new HashSet<Response>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ClothesMoodTag clothesMoodTag : set ) {
            set1.add( clothesMoodTagToResponse( clothesMoodTag ) );
        }

        return set1;
    }
}
