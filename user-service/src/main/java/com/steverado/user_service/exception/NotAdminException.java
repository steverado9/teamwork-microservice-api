package com.steverado.user_service.exception;

public class NotAdminException extends RuntimeException {

    public NotAdminException(String message) {
        super(message);
    }
}
