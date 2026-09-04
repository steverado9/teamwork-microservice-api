package com.steverado.feed_service.service.Impl;

import com.steverado.feed_service.dto.FeedItemDto;
import com.steverado.feed_service.entity.Article;
import com.steverado.feed_service.entity.Gif;
import com.steverado.feed_service.mapper.FeedMapper;
import com.steverado.feed_service.response.ApiResponse;
import com.steverado.feed_service.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final HttpServletRequest request;

    private final RestTemplate restTemplate;

    private final FeedMapper feedMapper;

    @Override
    public ResponseEntity<ApiResponse> viewAllArticlesAndGifs(int page, int size) {
        log.info("Received request to view articles and gifs with content size: {}", size);

        //created an empty list
        List<FeedItemDto> feed = new ArrayList<>();

        //get all the articles
        String articleUrl = "http://article-service/articles";

        String authorizationHeader1 = request.getHeader("Authorization");

        HttpHeaders headers1 = new HttpHeaders();
        headers1.set("Authorization", authorizationHeader1);

        HttpEntity<Void> entity1 = new HttpEntity<>(headers1);

        try {
            ResponseEntity<List<Article>> response = restTemplate.exchange(articleUrl, HttpMethod.GET, entity1, new ParameterizedTypeReference<List<Article>>() {});

            List<Article> articles = response.getBody();

            //used feedmapper to map the content of each article to the standard response feed article(FeedItemDto), then add it to the feed list
            articles.stream().forEach(article -> {
                feed.add(feedMapper.feedArticle(article));
            });

        } catch (Exception e) {
            System.out.println("error getting articles -> : " + e.getMessage());
        }

        //get all the gifs
        String gifUrl = "http://article-service/gifs";

        String authorizationHeader2 = request.getHeader("Authorization");

        HttpHeaders headers2 = new HttpHeaders();
        headers2.set("Authorization", authorizationHeader2);

        HttpEntity<Void> entity2 = new HttpEntity<>(headers2);

        try {
            ResponseEntity<List<Gif>> response = restTemplate.exchange(gifUrl, HttpMethod.GET, entity2, new ParameterizedTypeReference<List<Gif>>() {});

            List<Gif> gifs = response.getBody();

            gifs.forEach(gif -> {
                feed.add(feedMapper.feedGif(gif));
            });

        } catch (Exception e) {
            System.out.println("error getting gifs -> : " + e.getMessage());
        }

        //sort the feed by created at and reverse it.
        feed.sort(Comparator.comparing(FeedItemDto::getCreatedOn).reversed());

        int start = page * size; //calculate the start of the page
        int end = Math.min(start + size, feed.size()); //calculate the end of page

        if (start >= feed.size()) { //This is used to check if the page exists
            return ResponseEntity.ok(new ApiResponse("success", Collections.emptyList()));
        }

        List<FeedItemDto> paginatedFeed = feed.subList(start, end);
        log.info("Received request to view articles and gifs with content size: {}", paginatedFeed);

        return ResponseEntity.ok(new ApiResponse("success", paginatedFeed));
    }
}
