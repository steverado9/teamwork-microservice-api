package com.steverado.feed_service.service;

import com.steverado.feed_service.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface FeedService {

    public ResponseEntity<ApiResponse> viewAllArticlesAndGifs(int page, int size);
}
