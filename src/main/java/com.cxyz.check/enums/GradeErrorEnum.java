package com.cxyz.check.enums;

public enum GradeErrorEnum {

    GRADENOTFOUND("无此班级"),INNERERROR("服务器内部异常");
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private GradeErrorEnum(String msg)
    {
        this.msg = msg;
    }
}
