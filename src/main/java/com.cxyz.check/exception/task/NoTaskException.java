package com.cxyz.check.exception.task;

public class NoTaskException extends TaskException{

    public NoTaskException() {
    }

    public NoTaskException(String message) {
        super(message);
    }

    public NoTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTaskException(Throwable cause) {
        super(cause);
    }

}
