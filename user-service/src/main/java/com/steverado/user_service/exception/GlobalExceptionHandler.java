package com.steverado.user_service.exception;

import com.steverado.user_service.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    String error = "error";

    ApiResponse response = null;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(UserNotFoundException exception) {

        response = new ApiResponse<>(error, exception.getMessage());

        return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ApiResponse> handleNotAdmin(NotAdminException exception) {

        ApiResponse response = new ApiResponse<>(error, exception.getMessage());

        return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        ApiResponse response = new ApiResponse<>(error, "fields cannot be empty");

        return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleExceptions(Exception exception) {

        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {

            response = new ApiResponse(error, "The username or password is incorrect");

            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body(response);
        }

        if (exception instanceof SignatureException) {

            response = new ApiResponse(error, "The JWT signature is invalid");

            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body(response);

        }

        if (exception instanceof ExpiredJwtException) {

            response  = new ApiResponse(error, "The JWT token has expired");

            return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).body(response);

        }

        response = new ApiResponse(error, "Unknown internal server error.");

        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(response);
    }
}
