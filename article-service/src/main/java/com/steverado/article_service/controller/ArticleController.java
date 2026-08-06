package com.steverado.article_service.controller;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    public ResponseEntity<ApiResponse> createArticle(@Valid ArticleDto articleDto) {

        return articleService.saveArticle(articleDto);
    }
}
