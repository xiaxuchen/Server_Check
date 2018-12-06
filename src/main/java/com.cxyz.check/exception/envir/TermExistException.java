package com.cxyz.check.exception.envir;

/**
 * 当检查到已存在同学校，同学年同学期的term时抛出
 */
public class TermExistException extends EnvirException {

    public TermExistException() {
    }

    public TermExistException(String message) {
        super(message);
    }

    public TermExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TermExistException(Throwable cause) {
        super(cause);
    }
}
