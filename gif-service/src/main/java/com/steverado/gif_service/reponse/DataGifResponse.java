package com.steverado.gif_service.reponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataGifResponse {

    private Long gifId;

    private String message;

    private LocalDateTime createdOn;

    private String title;

    private String imageUrl;
}
