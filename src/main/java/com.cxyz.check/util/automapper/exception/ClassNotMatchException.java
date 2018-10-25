package com.cxyz.check.util.automapper.exception;

/**
 * 当对应映射的类不匹配将抛出此异常
 */
public class ClassNotMatchException extends RuntimeException{

    public ClassNotMatchException() {
    }

    public ClassNotMatchException(String message) {
        super(message);
    }

    public ClassNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassNotMatchException(Throwable cause) {
        super(cause);
    }
}
