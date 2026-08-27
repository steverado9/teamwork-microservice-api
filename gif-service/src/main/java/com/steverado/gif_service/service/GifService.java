package com.steverado.gif_service.service;

import com.steverado.gif_service.dto.GifDto;
import com.steverado.gif_service.entity.Gif;
import com.steverado.gif_service.reponse.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface GifService {
    Optional<Gif> getGifById(Long gifId);

    ResponseEntity<ApiResponse> saveGif(GifDto gifDto, MultipartFile file);

    ResponseEntity<ApiResponse> deleteGifById(Long id);

    ResponseEntity<ApiResponse> getGifAndCommentByGifId(Long gifId);

    List<Gif> getAllGifs();
}
