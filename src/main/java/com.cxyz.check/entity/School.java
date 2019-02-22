package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 学校实体
 */

public class School {
	
	private String name;//学校姓名
    private User manager;//学校管理员
    private Integer id;//学校编号
	private Term term;//当前学期
    
    public School(){}
    
    public School(Integer id) {
    	setId(id);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
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
				", manager=" + manager +
				", id=" + id +
				", term=" + term +
				'}';
	}
}
