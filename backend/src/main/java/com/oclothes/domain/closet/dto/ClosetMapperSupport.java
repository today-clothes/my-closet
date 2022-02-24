package com.oclothes.domain.closet.dto;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class ClosetMapperSupport {
    private final ClothesRepository clothesRepository;

    @Named("mapThumbnail")
    public String mapThumbnail(Closet closet) {
        Clothes clothes = this.clothesRepository.findFirstByClosetOrderByCreatedAtDesc(closet).orElse(null);
        if (isNull(clothes)) return null;
        return clothes.getImgUrl();
    }
}
