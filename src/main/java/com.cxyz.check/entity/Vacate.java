package com.cxyz.check.entity;

import java.sql.Timestamp;
/**
 * 请假表
 */
public class Vacate {

    private Integer id;//请假id

    private Timestamp start;//开始时间

    private Timestamp end;//结束时间

    private String des;//请假事由

    private User checker;//审核人

    private User sponsor;//申请人

    private Integer state;//请假情况

    private String checkInfo;//审核人回复

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public User getSponsor() {
        return sponsor;
    }

    public void setSponsor(User sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public String toString() {
        return "Vacate{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", des='" + des + '\'' +
                ", checker=" + checker +
                ", sponsor=" + sponsor +
                ", state=" + state +
                ", checkInfo='" + checkInfo + '\'' +
                '}';
    }
}
