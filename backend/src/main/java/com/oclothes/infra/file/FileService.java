package com.oclothes.infra.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String saveImage(MultipartFile file);

    byte[] getImage(String key);

    void delete(String key);

    void deleteAll(List<String> keys);
}
