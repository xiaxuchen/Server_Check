package com.cxyz.check.util.automapper.exception;

/**
 * 当对应的path反射出错时抛出
 */
public class PathNotFoundException extends RuntimeException{
    public PathNotFoundException() {
    }

    public PathNotFoundException(String message) {
        super(message);
    }

    public PathNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathNotFoundException(Throwable cause) {
        super(cause);
    }
}
