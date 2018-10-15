package com.cxyz.check.enums;

public enum UserErrorEnum {
    USERNOTFOUND("用户未注册"),PASSWORDERROR("密码输入错误"),INNERERROR("服务器内部异常");
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private UserErrorEnum(String msg)
    {
        this.msg = msg;
    }


}
