package com.oclothes.infra.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Profile("local")
@Service
public class LocalFileServiceImpl implements FileService {
    private static final String IMAGE_PATH = "./images/";

    @Override
    public String saveImage(MultipartFile file) {
        try {
            String extension = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename()));
            ImageExtension.validateImageExtension(extension);
            File destination = new File(String.format("%s%s.%s", IMAGE_PATH, UUID.randomUUID(), extension));
            destination.getParentFile().mkdirs();
            file.transferTo(destination.toPath());
            return destination.getPath().replace(IMAGE_PATH, "");
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("파일 저장에 실패했습니다.");
        }
    }

    @Override
    public byte[] getImage(String url) {
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(IMAGE_PATH + url))) {
            return IOUtils.toByteArray(bis);
        } catch (FileNotFoundException e) {
            throw new ImageNotFoundException();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
