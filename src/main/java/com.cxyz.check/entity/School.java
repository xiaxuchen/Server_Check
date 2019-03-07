package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 学校实体
 */

public class School {
	
	private String name;//学校姓名
	private String managerId;//管理员id
	private String managerPwd;//管理员密码
    private Integer id;//学校编号
	private Term term;//当前学期
    
    public School(){}
    
    public School(Integer id) {
    	setId(id);
    }

	public School(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerPwd() {
		return managerPwd;
	}

	public void setManagerPwd(String managerPwd) {
		this.managerPwd = managerPwd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	@Override
	public String toString() {
		return "School{" +
				"name='" + name + '\'' +
				", managerId='" + managerId + '\'' +
				", managerPwd='" + managerPwd + '\'' +
				", id=" + id +
				", term=" + term +
				'}';
	}
}
