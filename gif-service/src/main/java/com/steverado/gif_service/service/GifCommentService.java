package com.steverado.gif_service.service;

import com.steverado.gif_service.dto.CommentDto;
import com.steverado.gif_service.reponse.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface GifCommentService {
    ResponseEntity<ApiResponse> postComment(Long gifId, CommentDto commentDto);
}
