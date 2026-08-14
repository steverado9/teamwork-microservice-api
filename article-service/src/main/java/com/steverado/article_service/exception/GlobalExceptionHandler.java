package com.steverado.article_service.exception;

import com.steverado.article_service.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    String error = "error";

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ApiResponse> handleNotAdmin(NotAdminException exception) {

        ApiResponse response = new ApiResponse<>(error, exception.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

}
