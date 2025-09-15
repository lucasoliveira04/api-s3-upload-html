package com.api.s3.html.apis3html.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface S3Service {
    void uploadFile(MultipartFile file, String key) throws IOException;
}
