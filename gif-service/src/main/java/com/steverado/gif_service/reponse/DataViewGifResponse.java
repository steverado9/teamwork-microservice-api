package com.steverado.gif_service.reponse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class DataViewGifResponse<T> {

    private Long id;
    private String title;
    private String url;
    private LocalDateTime createdOn;
    private T comments;
}
