package com.cxyz.check.exception;

/**
 * 班级操作时可能会抛出的异常
 */
public class GradeException extends BaseException{
    public GradeException() {
    }

    public GradeException(String message) {
        super(message);
    }

    public GradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GradeException(Throwable cause) {
        super(cause);
    }
}
