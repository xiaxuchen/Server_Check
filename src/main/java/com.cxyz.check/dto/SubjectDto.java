package com.cxyz.check.dto;



import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.util.automapper.annotation.Classes;
import com.cxyz.check.util.automapper.annotation.Ignore;
import com.cxyz.check.util.automapper.annotation.Path;

import java.util.List;

/**
 * Created by 鱼塘主 on 2018/10/21.
 */
public class SubjectDto {
    //课程id
    private int id;

    /**
     * 课程名
     */
    private String name;

    private String time;

    /**
     * 教室
     */
    private String room;

    /**
     * 教师
     */
    private String teacher;

    /**
     * 第几周至第几周上
     */
    private List<Integer> weekList;

    /**
     * 开始上课的节次
     */
    private int start;

    /**
     * 上课节数
     */
    private int step;

    /**
     * 周几上
     */
    private int day;

    private String term;


    public SubjectDto() {
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setWeekList(List<Integer> weekList) {
        this.weekList = weekList;
    }

    public List<Integer> getWeekList() {
        return weekList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", room='" + room + '\'' +
                ", teacher='" + teacher + '\'' +
                ", weekList=" + weekList +
                ", start=" + start +
                ", step=" + step +
                ", day=" + day +
                ", term='" + term + '\'' +
                '}';
    }
}
