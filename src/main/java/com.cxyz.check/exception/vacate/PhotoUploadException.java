package com.cxyz.check.exception.vacate;

import com.cxyz.check.entity.Vacate;

public class PhotoUploadException extends VacateException {

    public PhotoUploadException() {
    }

    public PhotoUploadException(String message) {
        super(message);
    }

    public PhotoUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoUploadException(Throwable cause) {
        super(cause);
    }
}
