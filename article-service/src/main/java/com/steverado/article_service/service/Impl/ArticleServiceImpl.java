package com.steverado.article_service.service.Impl;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.entity.Article;
import com.steverado.article_service.mappers.ArticleMapper;
import com.steverado.article_service.repository.ArticleRepository;
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.response.DataArticleResponse;
import com.steverado.article_service.service.ArticleService;
import com.steverado.article_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final JwtService jwtService;

    private final ArticleMapper articleMapper;

    private final ArticleRepository articleRepository;

    //get user id
    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    //get article by id
    private Optional<Article> getArticleById(Long articleId) {
        return articleRepository.findByArticleId(articleId);
    }

    @Override
    public ResponseEntity<ApiResponse> saveArticle(ArticleDto articleDto) {

        Long userId = getUserId();

        Article article = articleMapper.toEntity(articleDto);
        article.setUserId(userId);

        articleRepository.saveArticle(article.getTitle(), article.getContent(), article.getUserId());

        Optional<Article> savedArticle = articleRepository.findArticleByUserId(userId);

        DataArticleResponse data = new DataArticleResponse();
        data.setMessage("Article successfully posted");
        data.setArticleId(savedArticle.get().getId());
        data.setTitle(article.getTitle());
        data.setCreatedOn(savedArticle.get().getCreatedAt());

        ApiResponse response = new ApiResponse("success", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse> updateArticle(Long articleId, Article article) {

        //get existing article with id
        Article existingArticle = getArticleById(articleId).orElseThrow();

        Long existingArticleUserId = existingArticle.getUserId();

        Long currentUserId = getUserId();


        return null;
    }


}
