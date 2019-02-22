package com.cxyz.check.entity;

import java.util.Date;
import java.util.List;

public class Term {

    //id
    private Integer id;

    //第几学期
    private Integer num;

    //所属学校
    private School school;

    //开学日期
    private Date begin;

    //每节课的时间
    private List<Times> times;

    public Term(){}

    public Term(Integer id){ setId(id); }

    public List<Times> getTimes() {
        return times;
    }

    public void setTimes(List<Times> times) {
        this.times = times;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", num=" + num +
                ", school=" + school +
                ", begin=" + begin +
                ", times=" + times +
                '}';
    }
}
