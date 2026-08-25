package com.steverado.gif_service.exception;

public class NotAdminException extends RuntimeException {

    public NotAdminException(String message) {
        super(message);
    }
}
