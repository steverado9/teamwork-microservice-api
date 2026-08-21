package com.steverado.gif_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GifDto {

    @NotBlank(message = "title field should not be empty")
    private String title;
}
