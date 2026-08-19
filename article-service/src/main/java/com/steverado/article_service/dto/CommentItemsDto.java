package com.steverado.article_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentItemsDto {

    private Long commentId;
    private String comment;
    private Long authorId;
}
