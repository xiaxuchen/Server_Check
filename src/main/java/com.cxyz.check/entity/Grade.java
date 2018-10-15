package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 */

public class Grade {
	private Integer id;//班级编号
	private String name;//班级名称
	private College college;//所属学院
	private Teacher headTeacher;//班主任
	private ClassRoom classRoom;//晚自习教室
	
	public Grade(){}

	public Grade(Integer id) {
		setId(id);
	}

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

	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	public Teacher getHeadTeacher() {
		return headTeacher;
	}
	public void setHeadTeacher(Teacher headTeacher) {
		this.headTeacher = headTeacher;
	}
	public ClassRoom getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}
	@Override
	public String toString() {
		return "Grade [name=" + name + ", college=" + college
				+ ", headTeacher=" + headTeacher + ", classRoom=" + classRoom
				+ ", id=" + id + "]";
	}
}
