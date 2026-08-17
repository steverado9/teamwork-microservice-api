package com.steverado.article_service.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataArticleCommentResponse {

    private String message;

    private LocalDateTime createdOn;

    private String articleTitle;

    private String article;

    private String comment;
}
