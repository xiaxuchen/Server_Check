package com.cxyz.check.dto;

import com.cxyz.check.json.CustomTimeStampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;

public class StatisticRecordDto {

    //学生信息
    private GradeStusDto stu;

    //考勤课程
    private String subject;

    //考勤结果
    private Integer result;

    //描述信息
    private String des;

    //考勤时间
    private Timestamp time;

    //考勤完成情况id
    private Integer compId;

    public GradeStusDto getStu() {
        return stu;
    }

    public void setStu(GradeStusDto stu) {
        this.stu = stu;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subjuct) {
        this.subject = subjuct;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @JsonSerialize(using = CustomTimeStampSerializer.class)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }


    @Override
    public String toString() {
        return "StatisticRecordDto{" +
                "subject"+subject+
                "stu=" + stu +
                ", result=" + result +
                ", des='" + des + '\'' +
                ", time=" + time +
                ", compId=" + compId +
                '}';
    }
}
