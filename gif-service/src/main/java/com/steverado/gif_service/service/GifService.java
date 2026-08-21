package com.steverado.gif_service.service;

import com.steverado.gif_service.dto.GifDto;
import com.steverado.gif_service.reponse.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface GifService {
    ResponseEntity<ApiResponse> saveGif(GifDto gifDto, MultipartFile file);
}
