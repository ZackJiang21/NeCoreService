package org.iiai.ne.exception;

public class NotPermittedException extends RuntimeException {
    public NotPermittedException() {
    }

    public NotPermittedException(String message) {
        super(message);
    }

    public NotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }
}
