package ism.absence.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ism.absence.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map<?, ?> response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return response.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload Cloudinary", e);
        }
    }

    @Override
    public String deleteFile(String publicId) {
        try {
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return result.get("result").toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression Cloudinary", e);
        }
    }
}