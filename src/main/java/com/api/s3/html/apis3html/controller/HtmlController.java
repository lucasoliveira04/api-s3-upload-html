package com.api.s3.html.apis3html.controller;

import com.api.s3.html.apis3html.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/s3/v1")
public class HtmlController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String fileName = file.getOriginalFilename();
        s3Service.uploadFile(file, fileName);

        return ResponseEntity.ok("Arquivo enviado com sucesso: " + fileName);
    }
}
