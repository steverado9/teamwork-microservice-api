package com.steverado.article_service.exception;

public class NotAdminException extends RuntimeException{

    public NotAdminException(String message) {
        super(message);
    }
}
