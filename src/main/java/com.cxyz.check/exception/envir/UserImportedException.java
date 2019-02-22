package com.cxyz.check.exception.envir;

/**
 * 用户已导入时抛出
 */
public class UserImportedException extends EnvirException{

    public UserImportedException() {
    }

    public UserImportedException(String message) {
        super(message);
    }

    public UserImportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserImportedException(Throwable cause) {
        super(cause);
    }
}
