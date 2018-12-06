package com.cxyz.check.entity;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 一个考勤完成情况就是一次考勤
 */
public class TaskCompletion{

    private Integer id;//考勤完成情况id
	private Timestamp start;
	private Timestamp end;
    private TaskInfo taskInfo;//所属任务id
    private Integer week;//考勤日期
    private Integer state;//完成情况
    private Timestamp updateTime;//更新时间

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public TaskCompletion(){}
    
    public TaskCompletion(int id){
    	setId(id);
    }

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer _id) {
		this.id = _id;
	}
	public TaskInfo getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(TaskInfo taskInfo) {
		this.taskInfo = taskInfo;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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

	@Override
	public String toString() {
		return "TaskCompletion{" +
				"id=" + id +
				", start=" + start +
				", end=" + end +
				", taskInfo=" + taskInfo +
				", week=" + week +
				", state=" + state +
				", updateTime=" + updateTime +
				'}';
	}
}
