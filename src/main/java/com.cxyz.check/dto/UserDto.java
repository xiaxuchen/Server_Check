package com.cxyz.check.dto;

import com.cxyz.check.entity.Student;
import com.cxyz.check.entity.Teacher;

public class UserDto {

    /**
     * 用户类别为学生
     */
    public static final int STUDENT = 0;

    /**
     * 用户类别为老师
     */
    public static final int TEACHER = 1;

    private String id;//编号
    private String name;//姓名
    private String sex;//性别
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
    private int power;

    /**
     * 该dto的类型
     */
    private int type;

    /**
     * dto为STUDENT和TEACHER时候拥有的学院名称
     */
    private String CollegeName;
    /**
     * dto为TEACHER时候拥有的学院id
     */
    private String collegeId;

    /**
     * dto为Student时候拥有的班级id
     */
    private int gradeId;

    /**
     * dto为STUDENT是拥有的班级名称
     */
    private String gradeName;

    /**
     * 成功返回老师数据时使用的方法
     * @param teacher
     */
    public UserDto(Teacher teacher) {
        this.setType(UserDto.TEACHER);
        this.setId(teacher.getId());
        this.setId(teacher.getId());
        this.setName(teacher.getName());
        this.setSex(teacher.getSex());
        this.setPhone(teacher.getPhone());
        this.setPhoto(teacher.getPhoto());
        this.setCollegeId(teacher.getId());
        this.setCollegeName(teacher.getCollege().getName());
        this.setPower(teacher.getPower());
    }

    /**
     * 成功返回学生时返回的方法
     * @param student
     */
    public UserDto(Student student)
    {
        this.setType(UserDto.STUDENT);
        this.setId(student.getId());
        this.setName(student.getName());
        this.setSex(student.getSex());
        this.setPhone(student.getPhone());
        this.setPhoto(student.getPhoto());
        this.setGradeId(student.getGrade().getId());
        this.setGradeName(student.getGrade().getName());
        this.setCollegeName(student.getCollegeName());
        this.setPower(student.getPower());
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCollegeName() {
        return CollegeName;
    }

    public void setCollegeName(String collegeName) {
        CollegeName = collegeName;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", power=" + power +
                ", type=" + type +
                ", CollegeName='" + CollegeName + '\'' +
                ", collegeId='" + collegeId + '\'' +
                ", gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                '}';
    }
}
