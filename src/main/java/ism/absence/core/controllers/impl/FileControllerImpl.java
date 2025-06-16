package ism.absence.core.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import ism.absence.core.controllers.FileController;
import ism.absence.core.dto.response.RestResponse;
import ism.absence.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "File Management", description = "APIs for files users")

@RequiredArgsConstructor
@RestController
public class FileControllerImpl implements FileController {
    private final CloudinaryService cloudinaryService;


    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String imageUrl = cloudinaryService.uploadFile(file);
        return ResponseEntity.ok(RestResponse.response(HttpStatus.OK, imageUrl, "file_uploaded"));
    }

    public ResponseEntity<?> deleteFile(@RequestParam("public_id") String publicId) {
        String result = cloudinaryService.deleteFile(publicId);
        return ResponseEntity.ok(RestResponse.response(HttpStatus.OK, result, "file_deleted"));
    }
}