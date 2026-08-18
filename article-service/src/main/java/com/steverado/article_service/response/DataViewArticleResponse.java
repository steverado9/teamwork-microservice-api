package com.steverado.article_service.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataViewArticleResponse<T> {
    private Long id;
    private String title;
    private String article;
    private LocalDateTime createdOn;
    private T comments;
}
