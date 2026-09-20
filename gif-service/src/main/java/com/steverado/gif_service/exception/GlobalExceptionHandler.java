package com.steverado.gif_service.exception;

import com.cloudinary.Api;
import com.steverado.gif_service.reponse.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    String error = "error";

    @ExceptionHandler(GifNotFoundException.class)
    public ResponseEntity<ApiResponse> handleGifNotFound(GifNotFoundException exception) {

        ApiResponse response = new ApiResponse(error, exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ApiResponse> handleNotAdmin(NotAdminException exception) {

        ApiResponse response = new ApiResponse<>(error, exception.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        ApiResponse response = new ApiResponse<>(error, "fields cannot be empty");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiResponse> handleMissingServletRequestPart(MissingServletRequestPartException exception) {

        ApiResponse response = new ApiResponse(error, "file is not present");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {

        ApiResponse response = new ApiResponse(error, "Send the right http request in the body");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

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
