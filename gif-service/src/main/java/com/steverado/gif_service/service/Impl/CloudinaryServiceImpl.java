package com.steverado.gif_service.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.steverado.gif_service.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {

        try {
            final Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            final String url = (String) result.get("secure_url");

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file" + e.getMessage(), e);
        }
    }
}
