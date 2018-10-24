package com.cxyz.check.entity;

/**
 * 考勤记录信息<br/>
 * 
 * Created by 夏旭晨 on 2018/9/23.
 */
public class CheckRecord {

	/**
	 * 考勤记录的id
	 */
    private Integer id;
    /**
     * 所属学生
     */
    private Student stu;
    /**
     * 考勤结果
     */
    private Integer result;
    /**
     * 完成情况，一般只需要装填id
     */
    private TaskCompletion comp;
    /**
     * 考勤描述信息
     */
    private String des;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStu() {
		return stu;
	}

	public void setStu(Student stu) {
		this.stu = stu;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public TaskCompletion getComp() {
		return comp;
	}

	public void setComp(TaskCompletion comp) {
		this.comp = comp;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Override
	public String toString() {
		return "CheckRecord{" +
				"id=" + id +
				", stu=" + stu +
				", result=" + result +
				", comp=" + comp +
				", des='" + des + '\'' +
				'}';
	}
}
