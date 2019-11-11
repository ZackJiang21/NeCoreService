package org.iiai.ne.exception;

public class HttpNotFoundException extends RuntimeException {
    public HttpNotFoundException() {
    }

    public HttpNotFoundException(String message) {
        super(message);
    }

    public HttpNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
