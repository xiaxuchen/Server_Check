package com.cxyz.check.exception.envir;

/**
 * 课程信息已导入时抛出
 */
public class LessonImportedException extends EnvirException{

    public LessonImportedException() {
    }

    public LessonImportedException(String message) {
        super(message);
    }

    public LessonImportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LessonImportedException(Throwable cause) {
        super(cause);
    }
}
