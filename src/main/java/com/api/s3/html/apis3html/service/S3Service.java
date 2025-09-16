package com.api.s3.html.apis3html.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

import java.io.IOException;
import java.util.List;

@Service
public interface S3Service {
    void uploadFile(MultipartFile file, String key) throws IOException;
    ListObjectsV2Request listAllFiles();
}
