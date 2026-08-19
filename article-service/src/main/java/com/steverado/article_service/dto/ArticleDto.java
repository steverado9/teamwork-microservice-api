package com.steverado.article_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleDto {

    @NotBlank(message = "title field should not be empty")
    private String title;

    @NotBlank(message = "content field should not be empty")
    private String content;
}
