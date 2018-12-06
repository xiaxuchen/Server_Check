package com.cxyz.check.dto;

import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.json.CustomDateSerializer;
import com.cxyz.check.json.CustomTimeStampSerializer;
import com.cxyz.check.util.automapper.annotation.Classes;
import com.cxyz.check.util.automapper.annotation.Path;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/10.
 * 检查当前是否有任务的dto
 */
@Classes(TaskCompletion.class)
public class CheckTaskDto {
    @Path("id")
    private int id;//任务id
    @Path("taskInfo.name")
    private String name;//任务名称
    @Path("taskInfo.sponsor.name")
    private String sponsorName;//发起者名称
    @Path("start")
    private Timestamp start;//开始时间
    @Path("end")
    private Timestamp end;//结束时间
    @Path("taskInfo.room.name")
    private String spot;//上课地点

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    @JsonSerialize(using= CustomTimeStampSerializer.class)
    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    @JsonSerialize(using= CustomTimeStampSerializer.class)
    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "CheckTaskDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sponsorName='" + sponsorName + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", spot='" + spot + '\'' +
                '}';
    }
}
