package com.steverado.article_service.controller;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.entity.Article;
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createArticle(@Valid @RequestBody ArticleDto articleDto) {

        return articleService.saveArticle(articleDto);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ApiResponse> updateArticle(
            @PathVariable Long articleId,
            @Valid @RequestBody Article article) {

        return articleService.updateArticle(articleId, article);
    }
}
