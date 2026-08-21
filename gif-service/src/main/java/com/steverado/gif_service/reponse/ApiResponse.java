package com.steverado.gif_service.reponse;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private String status;

    private T data;
}
