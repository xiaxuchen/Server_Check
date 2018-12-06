package com.cxyz.check.dto;

import java.util.Arrays;

public class AddTermDto {
    //开学日期
    private String date;

    //学期
    private int term;

    //各次课的时间
    private String times[];

    //学校id
    private int school;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String[] getTimes() {
        return times;
    }

    public void setTimes(String[] times) {
        this.times = times;
    }

    public int getSchool() {
        return school;
    }

    public void setSchool(int school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "AddTermDto{" +
                "date='" + date + '\'' +
                ", term=" + term +
                ", times=" + Arrays.toString(times) +
                ", school=" + school +
                '}';
    }
}
