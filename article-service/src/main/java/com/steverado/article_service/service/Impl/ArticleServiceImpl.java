package com.steverado.article_service.service.Impl;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.entity.User;
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final RestTemplate restTemplate;

    public Optional<User> authenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
          restTemplate.getForObject("http://user-service/ratingsData/users/", User.class);

        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> saveArticle(ArticleDto articleDto) {

        return null;
    }
}
