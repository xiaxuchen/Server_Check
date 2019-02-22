package com.cxyz.check.entity;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private Integer id;//考勤任务编号
    private Lesson lesson;
    private Integer weekday;//周几
    private Times start;//开始节次
    private Times end;//结束节次
    /*
     * 当前考勤任务的所有考勤情况
     */
    private List<TaskCompletion> completions = new ArrayList<TaskCompletion>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }


    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Times getStart() {
        return start;
    }

    public void setStart(Times start) {
        this.start = start;
    }

    public Times getEnd() {
        return end;
    }

    public void setEnd(Times end) {
        this.end = end;
    }

    public List<TaskCompletion> getCompletions() {
        return completions;
    }

    public void setCompletions(List<TaskCompletion> completions) {
        this.completions = completions;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", lesson=" + lesson +
                ", weekday=" + weekday +
                ", start=" + start +
                ", end=" + end +
                ", completions=" + completions +
                '}';
    }
}
