package com.steverado.article_service.service.Impl;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.entity.Article;
import com.steverado.article_service.entity.User;
import com.steverado.article_service.enums.Role;
import com.steverado.article_service.exception.NotAdminException;
import com.steverado.article_service.mappers.ArticleMapper;
import com.steverado.article_service.repository.ArticleRepository;
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.response.DataArticleResponse;
import com.steverado.article_service.response.DeleteDataResponse;
import com.steverado.article_service.response.UpdateArticleDataResponse;
import com.steverado.article_service.service.ArticleService;
import com.steverado.article_service.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final JwtService jwtService;

    private final ArticleMapper articleMapper;

    private final ArticleRepository articleRepository;

    private final RestTemplate restTemplate;

    private final HttpServletRequest request;

    //get user id
    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    //get article by id
    private Optional<Article> getArticleById(Long articleId) {
        return articleRepository.findByArticleId(articleId);
    }

//    private Exception isAdminOrCreator(Long articleId) {
//        //get existing article with id
//        Article existingArticle = getArticleById(articleId).orElseThrow();
//
//        Long existingUserId = existingArticle.getUserId();
//
//        Long currentUserId = getUserId();
//
//        String url = "http://user-service/auth/" + currentUserId;
//
//        String authorizationHeader = request.getHeader("Authorization");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authorizationHeader);
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        try {
//            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
//
//            User user = response.getBody();
//
//            if (user.getRole() != Role.ADMIN && user.getId() != existingUserId) {
//                return new NotAdminException("FORBIDDEN!");
//            }
//        } catch (Exception e) {
//            System.out.println("error getting user -> : " + e.getMessage());
//            return e;
//        }
//        return null;
//    }

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
    public ResponseEntity<ApiResponse> updateArticle(Long articleId, ArticleDto article) {

        //get existing article with id
        Article existingArticle = getArticleById(articleId).orElseThrow();

        Long existingUserId = existingArticle.getUserId();

        Long currentUserId = getUserId();

        String url = "http://user-service/auth/" + currentUserId;

        String authorizationHeader = request.getHeader("Authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);

            User user = response.getBody();

            if (user.getRole() != Role.ADMIN && user.getId() != existingUserId) {
                throw new NotAdminException("FORBIDDEN!");
            }
        } catch (Exception e) {
            System.out.println("error getting user -> : " + e.getMessage());
        }

        existingArticle.setTitle(article.getTitle());
        existingArticle.setContent(article.getContent());
        articleRepository.updateArticle(existingArticle.getTitle(), existingArticle.getContent(), existingUserId);

        UpdateArticleDataResponse data = new UpdateArticleDataResponse();
        data.setMessage("Article successfully updated");
        data.setArticle(existingArticle.getContent());
        data.setTitle(existingArticle.getTitle());

        ApiResponse apiResponse = new ApiResponse("Success", data);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteArticle(Long articleId) {

        //get existing article with id
        Article existingArticle = getArticleById(articleId).orElseThrow();

        Long existingUserId = existingArticle.getUserId();

        Long currentUserId = getUserId();

        String url = "http://user-service/auth/" + currentUserId;

        String authorizationHeader = request.getHeader("Authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);

            User user = response.getBody();

            if (user.getRole() != Role.ADMIN && user.getId() != existingUserId) {
                throw new NotAdminException("FORBIDDEN!");
            }
        } catch (Exception e) {
            System.out.println("error getting user -> : " + e.getMessage());
        }

        articleRepository.deleteArticleById(articleId);

        DeleteDataResponse data = new DeleteDataResponse();
        data.setMessage("Article successfully deleted");

        ApiResponse response = new ApiResponse("Success", data);

        return  ResponseEntity.ok(response);
    }
}


