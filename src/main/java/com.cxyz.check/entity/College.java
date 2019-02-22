package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 */

public class College {
	
	private Integer id;//学院编号
    private String name;//学院名称
    private School school;//所属学校
    private User manager;//系部管理员
	private Boolean userImport;//是否开启用户导入
	private Boolean lessonImport;//是否开启课程导入
    
    public College(){}
    
    public College(Integer id){
    	this.id = id;
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

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "College{" +
				"id=" + id +
				", name='" + name + '\'' +
				", school=" + school +
				", manager=" + manager +
				'}';
	}
}
