package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 */

public class Grade {
	private Integer id;//班级编号
	private String name;//班级名称
	private College college;//所属学院
	private Teacher manager;//班主任
	private ClassRoom room;//晚自习教室
	
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

	public Teacher getManager() {
		return manager;
	}

	public void setManager(Teacher manager) {
		this.manager = manager;
	}

	public ClassRoom getRoom() {
		return room;
	}

	public void setRoom(ClassRoom room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Grade{" +
				"id=" + id +
				", name='" + name + '\'' +
				", college=" + college +
				", manager=" + manager +
				", room=" + room +
				'}';
	}
}
