package com.steverado.gif_service.reponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataGifCommentResponse {

    private String message;

    private LocalDateTime createdOn;

    private String gifTitle;

    private String comment;
}
