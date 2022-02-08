package com.oclothes.infra.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Profile("prod")
@RequiredArgsConstructor
@Service
public class AwsS3FileServiceImpl implements FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public String saveImage(MultipartFile file) {
        ImageExtension.validateImageExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        final String key = UUID.randomUUID().toString();
        this.amazonS3Client.putObject(this.putObjectRequest(file, key));
        return key;
    }

    private PutObjectRequest putObjectRequest(MultipartFile file, String filename) {
        try {
            return new PutObjectRequest(this.bucketName, filename, file.getInputStream(), this.objectMetadata(file));
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new IllegalArgumentException("파일 저장에 실패했습니다.");
        }
    }

    private ObjectMetadata objectMetadata(MultipartFile file) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        return metadata;
    }

    @Override
    public byte[] getImage(String key) {
        try {
            return this.amazonS3Client.getObject(this.bucketName, key).getObjectContent().readAllBytes();
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new IllegalArgumentException("파일 불러오기에 실패했습니다.");
        }
    }

    @Override
    public void delete(String key) {
        this.amazonS3Client.deleteObject(this.bucketName, key);
    }
}
