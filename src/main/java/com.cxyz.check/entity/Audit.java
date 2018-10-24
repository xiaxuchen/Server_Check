package com.cxyz.check.entity;

import com.cxyz.check.util.date.DateTime;

public class Audit {
    //记录编号
    private Integer id;
    //审核人
    private User checker;
    //审核状态
    private Integer state;
    //上次更新时间
    private DateTime updateTime;
    //完成情况
    private TaskCompletion completion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public DateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(DateTime updateTime) {
        this.updateTime = updateTime;
    }

    public TaskCompletion getCompletion() {
        return completion;
    }

    public void setCompletion(TaskCompletion completion) {
        this.completion = completion;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", checker=" + checker +
                ", state=" + state +
                ", updateTime=" + updateTime +
                ", completion=" + completion +
                '}';
    }
}
