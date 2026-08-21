package com.steverado.gif_service.service.Impl;

import com.steverado.gif_service.dto.GifDto;
import com.steverado.gif_service.reponse.ApiResponse;
import com.steverado.gif_service.service.GifService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GifServiceImpl implements GifService {

    //get user id
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    @Override
    public ResponseEntity<ApiResponse> saveGif(GifDto gifDto, MultipartFile file) {

        Long userId = getUserId();


        return null;
    }
}
