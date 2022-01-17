package com.oclothes.infra.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Profile("local")
@Component
public class LocalFileServiceImpl implements FileService {
    @Override
    public String saveImage(MultipartFile file) {
        try {
            String extension = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename()));
            ImageExtension.validateImageExtension(extension);
            File destination = new File(String.format("./images/%s.%s", UUID.randomUUID(), extension));
            destination.getParentFile().mkdirs();
            file.transferTo(destination.toPath());
            return destination.getPath();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("파일 저장에 실패했습니다.");
        }
    }
}
