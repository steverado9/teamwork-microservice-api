package com.steverado.feed_service.controller;

import com.steverado.feed_service.response.ApiResponse;
import com.steverado.feed_service.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feeds")
    public ResponseEntity<ApiResponse> feed(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return feedService.viewAllArticlesAndGifs(page, size);
    }
}
