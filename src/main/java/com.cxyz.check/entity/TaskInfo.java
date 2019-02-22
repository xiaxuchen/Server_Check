package com.cxyz.check.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 考勤任务基本信息
 */

public class TaskInfo {
    private Integer id;//考勤任务编号
	private Lesson lesson;//课程id
	private String name;//考勤任务名称
    private User sponsor;//考勤任务发起人
    private User checker;//考勤任务考勤人
	private Integer weekday;//周几
    private ClassRoom room;//考勤所在地
    private Integer type;//考勤任务类型，临时任务或者课程
    private Grade grade = new Grade();//考勤班级
	private Times start;//开始节次
	private Times end;//结束节次
	private Term term;//学期
	private Integer num;//教务系统中的课程编号
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public User getSponsor() {
		return sponsor;
	}

	public void setSponsor(User sponsor) {
		this.sponsor = sponsor;
	}

	public User getChecker() {
		return checker;
	}

	public void setChecker(User checker) {
		this.checker = checker;
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

	public List<TaskCompletion> getCompletions() {
		return completions;
	}

	public void setCompletions(List<TaskCompletion> completions) {
		this.completions = completions;
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

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	@Override
	public String toString() {
		return "TaskInfo{" +
				"id=" + id +
				", lesson=" + lesson +
				", name='" + name + '\'' +
				", sponsor=" + sponsor +
				", checker=" + checker +
				", weekday=" + weekday +
				", room=" + room +
				", type=" + type +
				", grade=" + grade +
				", start=" + start +
				", end=" + end +
				", term=" + term +
				", num=" + num +
				", completions=" + completions +
				'}';
	}
}
