package com.cxyz.check.entity;
/**
 * Created by 夏旭晨 on 2018/9/23.
 */

public class User {
	private String id;//编号
    private String name;//姓名
    private String sex;//性别
    private String pwd;//密码
    private String phone;//电话号码
    private String photo;//照片的url
    
    /**
     * power属性用来区分权限
     * 0为普通学生权限
     * 5为考勤人权限
     * 30为普通任课老师权限
     * 35为班主任权限
     * 45为系部管理员权限
     * 55为校级管理员权限
     * 100为超级管理员权限
     */
    private Integer power;

	/**
	 * 用户类型
	 */
	private Integer type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}


	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", sex='" + sex + '\'' +
				", pwd='" + pwd + '\'' +
				", phone='" + phone + '\'' +
				", photo='" + photo + '\'' +
				", power=" + power +
				", type=" + type +
				'}';
	}
}


