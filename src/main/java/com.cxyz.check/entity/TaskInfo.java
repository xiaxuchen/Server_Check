package com.cxyz.check.entity;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 考勤任务基本信息
 */

public class TaskInfo {
    private String id;//考勤任务编号
	private String name;//考勤任务名称
    private User sponsor = new User();//考勤任务发起人
    private User checker = new User();//考勤任务考勤人
    private Timestamp start;//考勤开始时间
    private Timestamp end;//考勤时限
    private ClassRoom room;//考勤所在地
    private Integer type;//考勤任务类型，临时任务或者课程
    private Grade grade = new Grade();//考勤班级
    /*
     * 当前考勤任务的所有考勤情况
     */
    private List<TaskCompletion> completions = new ArrayList<TaskCompletion>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public User getChecker() {
		return checker;
	}

	public void setChecker(User checker) {
		this.checker = checker;
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

	@Override
	public String toString() {
		return "TaskInfo{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", sponsor=" + sponsor +
				", checker=" + checker +
				", start=" + start +
				", end=" + end +
				", room=" + room +
				", type=" + type +
				", grade=" + grade +
				", completions=" + completions +
				'}';
	}
}
