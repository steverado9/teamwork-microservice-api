package com.steverado.feed_service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Article {

    private Long id;

    private final String title;

    private final String content;

    private Long userId;

    private LocalDateTime createdAt;

}
