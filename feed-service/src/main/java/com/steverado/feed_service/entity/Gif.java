package com.steverado.feed_service.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Gif {

    private Long id;

    private final String imageUrl;

    private final String title;

    private Long userId;

    private LocalDateTime createdAt;

}
