package com.steverado.feed_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedItemDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdOn;
    private Long authorId;
}
