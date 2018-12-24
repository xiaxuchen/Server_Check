package com.cxyz.check.exception.vacate;

import com.cxyz.check.exception.BaseException;

/**
 * 请假时出现异常则抛出
 */
public class VacateException extends BaseException {

    public VacateException() {
    }

    public VacateException(String message) {
        super(message);
    }

    public VacateException(String message, Throwable cause) {
        super(message, cause);
    }

    public VacateException(Throwable cause) {
        super(cause);
    }
}
