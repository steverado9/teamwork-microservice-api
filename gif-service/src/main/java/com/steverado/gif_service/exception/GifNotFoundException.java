package com.steverado.gif_service.exception;

public class GifNotFoundException extends RuntimeException{

    public GifNotFoundException(String message) {
        super(message);
    }
}
