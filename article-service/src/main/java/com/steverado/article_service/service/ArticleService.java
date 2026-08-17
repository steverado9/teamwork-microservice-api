package com.steverado.article_service.service;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.entity.Article;
import com.steverado.article_service.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ArticleService {
    ResponseEntity<ApiResponse> saveArticle(ArticleDto articleDto);

    ResponseEntity<ApiResponse> updateArticle(Long articleId, ArticleDto input);

    ResponseEntity<ApiResponse> deleteArticle(Long articleId);

    Optional<Article> getArticleById(Long articleId);

    Long getUserId();

    ResponseEntity<ApiResponse> getArticleAndCommentById(Long articleId);
}
