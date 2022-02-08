package com.oclothes.domain.clothes.dto;

import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.closet.exception.ClosetNotFoundException;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.infra.file.FileService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ClothesMapperSupport {
    private final FileService fileService;
    private final ClosetRepository closetRepository;

    @Named("saveImage")
    public String saveImage(MultipartFile file) throws IOException {
        return this.fileService.saveImage(file);
    }

    @Named("findByClosetId")
    public Closet findByClosetId(Long id) {
        return this.closetRepository.findByIdAndUser(id, SecurityUtils.getLoggedInUser()).orElseThrow(ClosetNotFoundException::new);
    }
}
