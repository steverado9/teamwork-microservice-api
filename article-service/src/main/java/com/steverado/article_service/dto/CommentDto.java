package com.steverado.article_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDto {

    @NotBlank(message = "comment field should not be empty")
    private String comment;
}
