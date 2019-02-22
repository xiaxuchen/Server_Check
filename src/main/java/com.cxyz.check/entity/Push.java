package com.cxyz.check.entity;

import java.sql.Timestamp;

public class Push {

    private Integer id;//推送信息id

    private Timestamp sendTime;//发送时间

    private User receiver;//接收者

    private String info;//信息

    private Integer state;//状态

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Push{" +
                "id=" + id +
                ", sendTime=" + sendTime +
                ", receiver=" + receiver +
                ", info='" + info + '\'' +
                ", state=" + state +
                '}';
    }
}
