package ism.absence.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(MultipartFile file);
    String deleteFile(String publicId);
}