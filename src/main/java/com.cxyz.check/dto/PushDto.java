package com.cxyz.check.dto;

import com.cxyz.check.json.CustomTimeStampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;

public class PushDto {
    private Integer id;//推送信息id

    private Timestamp sendTime;//发送时间

    private String info;//信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonSerialize(using = CustomTimeStampSerializer.class)
    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PushDto{" +
                "id=" + id +
                ", sendTime=" + sendTime +
                ", info='" + info + '\'' +
                '}';
    }
}
