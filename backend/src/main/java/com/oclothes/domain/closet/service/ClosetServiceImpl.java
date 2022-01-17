package com.oclothes.domain.closet.service;

import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.closet.dto.ClosetMapper;
import com.oclothes.domain.closet.exception.ClosetNotEmptyException;
import com.oclothes.domain.closet.exception.ClosetNotFoundException;
import com.oclothes.domain.clothes.service.ClothesService;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oclothes.domain.closet.dto.ClosetDto.*;
import static com.oclothes.global.config.security.util.SecurityUtils.getLoggedInUser;

@Transactional
@RequiredArgsConstructor
@Service
public class ClosetServiceImpl implements ClosetService {
    private final ClosetMapper closetMapper;
    private final ClosetRepository closetRepository;
    private final ClothesService clothesService;

    @Override
    public CreateResponse create(CreateRequest request) {
        return this.closetMapper.entityToCreateResponse(this.closetRepository.save(this.closetMapper.toEntity(request)));
    }

    @Override
    public SliceDto<DefaultResponse> findAllSliceByUser(Pageable pageable) {
        return SliceDto.create(this.closetRepository.findAllSliceByUser(getLoggedInUser(), pageable).map(this.closetMapper::entityToDefaultResponse));
    }

    @Override
    public DefaultResponse updateName(Long id, NameUpdateRequest request) {
        return this.closetMapper.entityToDefaultResponse(this.findByUserAndClosetId(id).setName(request.getName()));
    }

    @Override
    public DefaultResponse changeLockStatus(Long id) {
        return this.closetMapper.entityToDefaultResponse(this.findByUserAndClosetId(id).changeLockedStatus());
    }

    @Override
    public void delete(Long id) {
        final Closet closet = this.findByUserAndClosetId(id);
        this.validateClothesIsEmpty(closet);
        this.closetRepository.delete(closet);
    }

    private void validateClothesIsEmpty(Closet closet) {
        if (this.clothesService.getSizeByCloset(closet) > 0) throw new ClosetNotEmptyException();
    }

    private Closet findByUserAndClosetId(Long id) {
        return this.closetRepository.findByIdAndUser(id, SecurityUtils.getLoggedInUser()).orElseThrow(ClosetNotFoundException::new);
    }
}
