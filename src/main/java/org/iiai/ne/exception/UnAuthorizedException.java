package org.iiai.ne.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException() {
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
}
