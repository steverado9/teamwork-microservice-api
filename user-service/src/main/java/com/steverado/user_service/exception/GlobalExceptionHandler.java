package com.steverado.user_service.exception;

import com.steverado.user_service.response.ApiResponse;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralError(Exception exception) {
        ApiResponse response = new ApiResponse("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(response);
    }
}
