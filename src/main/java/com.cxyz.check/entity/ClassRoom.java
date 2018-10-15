package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 */

public class ClassRoom {
    private Integer id;//教室编号
    private String name;//教室名称
    private College college;//所属学院
    private Integer state;//是否空闲状态(预留字段)
    
    public ClassRoom(){}
    
    public ClassRoom(Integer id){
    	setId(id);
    }
    
	public College getCollege() {
		return college;
	}
	
	public void setCollege(College c)
	{
		college = c;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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

	@Override
	public String toString() {
		return "ClassRoom{" +
				"id=" + id +
				", name='" + name + '\'' +
				", college=" + college +
				", state=" + state +
				'}';
	}
}
