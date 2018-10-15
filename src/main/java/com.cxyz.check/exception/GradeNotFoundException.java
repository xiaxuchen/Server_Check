package com.cxyz.check.exception;

public class GradeNotFoundException extends GradeException {
    public GradeNotFoundException() {
    }

    public GradeNotFoundException(String message) {
        super(message);
    }

    public GradeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GradeNotFoundException(Throwable cause) {
        super(cause);
    }
}
