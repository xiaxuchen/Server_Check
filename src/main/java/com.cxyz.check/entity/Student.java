package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 学生实体
 */

public class Student extends OldUser {

	//更多信息在User中

	private Grade grade;//所属班级
	
	private String collegeName;//学院名称
	
	public Student(){
		this(null);
	}
	
	public Student(String id){
		this(id,null);
	}
	
	public Student(String id,String pwd)
	{
		if(id != null)
			this.setId(id);
		if(pwd != null)
			this.setPwd(pwd);
	}
	
    public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	@Override
	public String toString() {
		return "Student{" +super.toString()+
				"grade=" + grade +
				", collegeName='" + collegeName + '\'' +
				'}';
	}
}
