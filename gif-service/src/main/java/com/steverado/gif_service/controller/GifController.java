package com.steverado.gif_service.controller;

import com.steverado.gif_service.dto.GifDto;
import com.steverado.gif_service.reponse.ApiResponse;
import com.steverado.gif_service.service.GifService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class GifController {

    private final GifService gifService;

    private ResponseEntity<ApiResponse> postGif(
            @Valid @ModelAttribute GifDto gifDto,
            @RequestPart MultipartFile file
    ) {
        return gifService.saveGif(gifDto, file);
    }
}
