package com.oclothes.domain.closet.dto;

import com.oclothes.domain.clothes.domain.Clothes;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ClosetMapperSupport {

    @Named("getImgUrl")
    public String getImgUrl(List<Clothes> clothes) {
        if ((clothes != null) && !clothes.isEmpty()){
            return clothes.get(clothes.size() - 1).getImgUrl();
        }
        else{
            return "";
        }
    }
}
