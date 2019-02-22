package com.cxyz.check.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程实体
 */
public class Lesson {

    private Integer id;//考勤任务编号
    private String name;//考勤任务名称
    private User sponsor = new User();//考勤任务发起人
    private User checker = new User();//考勤任务考勤人
    private ClassRoom room = new ClassRoom();//考勤所在地
    private Integer type;//考勤任务类型，临时任务或者课程
    private Grade grade = new Grade();//考勤班级
    private Term term = new Term();
    private String num;//编号
    private List<Task> tasks = new ArrayList<>();

    public Lesson(){}

    public Lesson(Integer id){setId(id);}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getSponsor() {
        return sponsor;
    }

    public void setSponsor(User sponsor) {
        this.sponsor = sponsor;
    }

    public ClassRoom getRoom() {
        return room;
    }

    public void setRoom(ClassRoom room) {
        this.room = room;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sponsor=" + sponsor +
                ", checker=" + checker +
                ", room=" + room +
                ", type=" + type +
                ", grade=" + grade +
                ", term=" + term +
                ", num='" + num + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
