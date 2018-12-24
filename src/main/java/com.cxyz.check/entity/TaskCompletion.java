package com.cxyz.check.entity;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 一个考勤完成情况就是一次考勤
 */
public class TaskCompletion{

    private Integer id;//考勤完成情况id
    private TaskInfo taskInfo;//所属任务id
    private Integer week;//考勤周次
	private Date date;//考勤日期
    private Integer state;//完成情况
    private Timestamp updateTime;//更新时间

	private List<CheckRecord> records;//考勤记录

	private OtherState otherState;//其他情况

	public List<CheckRecord> getRecords() {
		return records;
	}

	public void setRecords(List<CheckRecord> records) {
		this.records = records;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OtherState getOtherState() {
		return otherState;
	}

	public void setOtherState(OtherState otherState) {
		this.otherState = otherState;
	}

	@Override
	public String toString() {
		return "TaskCompletion{" +
				"id=" + id +
				", taskInfo=" + taskInfo +
				", week=" + week +
				", date=" + date +
				", state=" + state +
				", updateTime=" + updateTime +
				", records=" + records +
				", otherState=" + otherState +
				'}';
	}
}
