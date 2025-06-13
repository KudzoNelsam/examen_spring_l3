package ism.absence.core.controllers;

import ism.absence.core.dto.response.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/files")
public interface FileController {
    @PostMapping("/upload")
    ResponseEntity<RestResponse<String>> uploadFile(@RequestParam("file") MultipartFile file);
    @DeleteMapping("/delete")
    ResponseEntity<RestResponse<String>> deleteFile(@RequestParam("public_id") String publicId);
}