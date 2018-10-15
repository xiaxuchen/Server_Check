package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 * 老师实体
 */

public class Teacher extends User {

	//更多信息看User
	
	private College college;//所属学院
	
	public Teacher(){
		this(null);
	}
	
	public Teacher(String id)
	{
		this(id,null);
	}
	
	public Teacher(String id,String pwd)
	{
		if(id != null)
			this.setId(id);
		if(pwd != null)
			this.setPwd(pwd);
	}
	
    public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "Teacher{" +super.toString()+
				"college=" + college +
				'}';
	}
}
