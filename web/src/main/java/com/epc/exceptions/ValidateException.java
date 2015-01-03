package com.epc.exceptions;

/**
 *
 */
public class ValidateException extends Exception {

    private Exception exception;

    public ValidateException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
