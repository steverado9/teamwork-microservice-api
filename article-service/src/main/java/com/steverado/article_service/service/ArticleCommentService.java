package com.steverado.article_service.service;

import com.steverado.article_service.dto.CommentDto;
import com.steverado.article_service.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface ArticleCommentService {

    ResponseEntity<ApiResponse> saveComment(Long articleId, CommentDto commentDto);
}
