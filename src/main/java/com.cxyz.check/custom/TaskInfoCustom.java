package com.cxyz.check.custom;

import com.cxyz.check.entity.TaskInfo;

public class TaskInfoCustom extends TaskInfo {

    private Integer sign;//标识

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "TaskInfoCustom{" +
                "sign=" + sign +
                "super="+super.toString()+
                '}';
    }
}
