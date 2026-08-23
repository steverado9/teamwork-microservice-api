package com.steverado.gif_service.service.Impl;

import com.steverado.gif_service.dto.GifDto;
import com.steverado.gif_service.entity.Gif;
import com.steverado.gif_service.reponse.ApiResponse;
import com.steverado.gif_service.reponse.DataGifResponse;
import com.steverado.gif_service.repository.GifRepository;
import com.steverado.gif_service.service.CloudinaryService;
import com.steverado.gif_service.service.GifService;
import com.steverado.gif_service.util.FileUploadUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GifServiceImpl implements GifService {

    private CloudinaryService cloudinaryService;

    private GifRepository gifRepository;

    //get user id
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    @Override
    public ResponseEntity<ApiResponse> saveGif(GifDto gifDto, MultipartFile file) {

        Long userId = getUserId();

        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);

        final String image_url = cloudinaryService.uploadFile(file);

        Gif gif = new Gif();
        gif.setTitle(gifDto.getTitle());
        gif.setImageUrl(image_url);
        gif.setUserId(userId);

        gifRepository.saveGif(gif.getImageUrl(), gif.getTitle(), gif.getUserId());

        Gif savedGif = gifRepository.findGifByUserId(userId);

        DataGifResponse data = new DataGifResponse();

        data.setGifId(savedGif.getId());
        data.setMessage("Gif Image Successfully Posted");
        data.setCreatedOn(savedGif.getCreatedAt());
        data.setTitle(gif.getTitle());
        data.setImageUrl(gif.getImageUrl());

        ApiResponse<DataGifResponse> response = new ApiResponse<>();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteGifById(Long id) {

        Long userId = getUserId();

        Optional<Gif> existingGif = gifRepository.findGifById(id);

        return null;
    }
}
