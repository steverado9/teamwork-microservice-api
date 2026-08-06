package com.steverado.article_service.service;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface ArticleService {
    ResponseEntity<ApiResponse> saveArticle(ArticleDto articleDto);
}
