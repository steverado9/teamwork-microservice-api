package com.steverado.article_service.controller;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.dto.CommentDto;
<<<<<<< HEAD
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.service.ArticleCommentService;
import com.steverado.article_service.service.ArticleService;
=======
import com.steverado.article_service.entity.Article;
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.service.ArticleCommentService;
import com.steverado.article_service.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
>>>>>>> fe30fbc2a1bd1887e71828b7b30ebf900ef75c16
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    private final ArticleCommentService articleCommentService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createArticle(@Valid @RequestBody ArticleDto articleDto) {

        return articleService.saveArticle(articleDto);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ApiResponse> updateArticle(
            @PathVariable Long articleId,
            @Valid @RequestBody ArticleDto input) {

        return articleService.updateArticle(articleId, input);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<ApiResponse> deleteArticle(@PathVariable Long articleId) {

        return articleService.deleteArticle(articleId);
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<ApiResponse> addComment(@PathVariable Long articleId, @Valid @RequestBody CommentDto commentDto) {

        return articleCommentService.saveComment(articleId, commentDto);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ApiResponse> viewArticle(@PathVariable Long articleId) {
        return articleService.getArticleAndCommentById(articleId);
    }
}
