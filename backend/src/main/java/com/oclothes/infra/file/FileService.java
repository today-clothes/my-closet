package com.oclothes.infra.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveImage(MultipartFile file);
}
