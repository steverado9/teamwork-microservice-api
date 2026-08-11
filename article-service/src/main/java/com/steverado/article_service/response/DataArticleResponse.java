package com.steverado.article_service.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataArticleResponse {

    private String message;

    private Long articleId;

    private LocalDateTime createdOn;

    private String title;
}
