package com.cxyz.check.entity;

import java.sql.Time;
import java.sql.Timestamp;

public class Times {

    //id
    private Integer id;

    //节次
    private Integer session;

    //开始时间
    private Timestamp start;

    //结束时间
    private Timestamp end;

    //所属学期
    private Term term;

    public Times(){}

    public Times(Integer session) {
        this.session = session;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
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

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    public String toString() {
        return "Times{" +
                "id=" + id +
                ", session=" + session +
                ", start=" + start +
                ", end=" + end +
                ", term=" + term +
                '}';
    }
}
