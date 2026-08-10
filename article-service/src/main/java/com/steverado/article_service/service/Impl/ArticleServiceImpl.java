package com.steverado.article_service.service.Impl;

import com.steverado.article_service.dto.ArticleDto;
import com.steverado.article_service.entity.User;
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.service.ArticleService;
import com.steverado.article_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final JwtService jwtService;

    @Override
    public ResponseEntity<ApiResponse> saveArticle(ArticleDto articleDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return null;
    }
}
