package com.oclothes.domain.closet.service;

import com.oclothes.global.dto.SliceDto;
import org.springframework.data.domain.Pageable;

import static com.oclothes.domain.closet.dto.ClosetDto.*;

public interface ClosetService {
    CreateResponse create(CreateRequest request);

    SliceDto<DefaultResponse> findAllSliceByUser(Pageable pageable);

    DefaultResponse updateName(Long id, NameUpdateRequest request);

    void delete(Long id);
}
