package com.api.s3.html.apis3html.controller;

import com.api.s3.html.apis3html.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/s3/v1")
public class HtmlController {
    private static int count = 1;
    private final S3Service s3Service;
    private final S3Client s3Client;

    @GetMapping(path = "/list", produces = "application/json")
    public ResponseEntity<List<String>> listAllFiles() {
        ListObjectsV2Request request = s3Service.listAllFiles();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        List<String> keys = response.contents().stream()
                .map(S3Object::key)
                .toList();

        return ResponseEntity.ok(keys);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
            ListObjectsV2Request request = s3Service.listAllFiles();
            ListObjectsV2Response response = s3Client.listObjectsV2(request);

            int lastUser = response.contents().stream()
                    .map(S3Object::key)
                    .filter(key -> key.startsWith("user"))
                    .map(key -> {
                        try {
                            String numberObject = key.split("/")[0].replace("user", "");
                            return Integer.parseInt(numberObject);
                        } catch (Exception e) {
                            return 0;
                        }
                    })
                    .max(Integer::compareTo)
                    .orElse(0);

            int nextUser = lastUser + 1;

            UUID uuid = UUID.randomUUID();
            String fileName = "user" + nextUser + "/" + uuid + "-" + file.getOriginalFilename();

            s3Service.uploadFile(file, fileName);

            return ResponseEntity.ok(fileName);
    }
}
