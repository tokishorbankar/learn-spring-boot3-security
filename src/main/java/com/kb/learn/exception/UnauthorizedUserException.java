package com.kb.learn.exception;

public final class UnauthorizedUserException extends RuntimeException {

    public UnauthorizedUserException() {
    }

    public UnauthorizedUserException(final String message) {
        super(message);
    }

    public UnauthorizedUserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedUserException(final Throwable cause) {
        super(cause);
    }

    public UnauthorizedUserException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
