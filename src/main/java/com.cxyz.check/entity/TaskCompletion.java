package com.cxyz.check.entity;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 一个考勤完成情况就是一次考勤
 */
public class TaskCompletion{
	/**
	 * 正常考勤
	 */
	public static final int NORMAL = 0;
	/**
	 * 特殊情况
	 */
	public static final int OTHER = -1;
    private Integer id;//考勤完成情况id
    private TaskInfo taskInfo;//所属任务id
    private Date date;//考勤日期
    private Integer state;//完成情况
    private Timestamp updatetime;//更新时间

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public TaskCompletion(){}
    
    public TaskCompletion(int id){
    	setId(id);
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

	@Override
	public String toString() {
		return "TaskCompletion [_id=" + id + ", taskInfo=" + taskInfo
				+ ", date=" + date + ", state=" + state + ", updatetime="
				+ updatetime + "]";
	}

	
}
