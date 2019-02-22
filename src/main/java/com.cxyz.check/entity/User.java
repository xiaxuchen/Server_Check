package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 */

public class User{

    //班级信息，学生和班主任仅有
    private Grade grade;
    //学院信息
    private College college;
    //用户id
    private String id;
    //用户名
    private String name;
    //性别
    private String sex;
    //密码
    private String pwd;
    //电话号码
    private String phone;
    //照片地址
    private String photo;
    //邮箱
    private String email;

    /**
     * power属性用来区分权限
     * 0为普通学生权限
     * 5为考勤人权限
     * 30为普通任课老师权限
     * 35为班主任权限
     * 45为系部管理员权限
     * 55为校级管理员权限
     * 100为超级管理员权限
     * 默认为普通学生权限
     */
    private Integer power = 0;
    /**
     * type属性用来区分学生和老师！
     * 0为学生
     * 1为老师
     */
    private Integer type;

    public User(){}

    public User(String id)
    {
        setId(id);
    }

    public User(String id,Integer type)
    {
        setId(id);
        setType(type);
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "grade=" + grade +
                ", college=" + college +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                ", power=" + power +
                ", type=" + type +
                '}';
    }
}


