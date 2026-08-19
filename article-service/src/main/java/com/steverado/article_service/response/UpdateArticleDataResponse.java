package com.steverado.article_service.response;

import lombok.Data;

@Data
public class UpdateArticleDataResponse {

    private String message;

    private String title;

    private String article;
}
