package com.cxyz.check.dto;

import com.cxyz.check.util.date.Date;

import java.io.Serializable;

public class TaskDto implements Serializable {

    /**
     * 临时考勤
     */
    public static final int PARTTIME = 0;
    /**
     * 课程考勤
     */
    public static final int LESSON = 1;
    /**
     * 课任老师或考勤发起人
     */
    private LoginDto sponsor;

    /**
     * 考勤人
     */
    private LoginDto checker;

    /**
     * 课程名字
     */
    private String name;

    /**
     * 任务类型，在TaskState中取
     */
    private int type;

    /**
     * 开始时间
     */
    private Date start;


    /**
     * 结束时间
     */
    private Date end;

    /**
     * 考勤教室
     */
    private String room_name;

    public LoginDto getSponsor() {
        return sponsor;
    }

    public void setSponsor(LoginDto sponsor) {
        this.sponsor = sponsor;
    }

    public LoginDto getChecker() {
        return checker;
    }

    public void setChecker(LoginDto checker) {
        this.checker = checker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

}
