package com.steverado.feed_service.exception;

import com.steverado.feed_service.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    String error = "error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exception) {

        exception.printStackTrace();

        if (exception instanceof SignatureException) {

            ApiResponse response = new ApiResponse(error, "The JWT signature is invalid");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (exception instanceof ExpiredJwtException) {

            ApiResponse response  = new ApiResponse(error, "The JWT token has expired");

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        }

        ApiResponse response = new ApiResponse(error, "Unknown internal server error.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR). body(response);

    }
}
