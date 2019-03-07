package com.cxyz.check.service;

import com.cxyz.check.dto.AddTermDto;
import com.cxyz.check.dto.GradeDto;
import com.cxyz.check.entity.College;
import com.cxyz.check.entity.Grade;
import com.cxyz.check.entity.School;
import com.cxyz.check.exception.envir.UserImportedException;
import com.cxyz.check.nexception.DataConflictException;
import com.cxyz.check.nexception.DataNotFoundException;
import com.cxyz.check.nexception.InternalServerException;
import com.cxyz.check.nexception.ParameterInvalidException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface EnvirService {

    /**
     * 添加学期
     * @param dto
     */
    void addTerm(AddTermDto dto);

    /**
     * 获取学院所有班级id和name
     * @param collegeId
     * @return
     */
    List<GradeDto> getCollegeGrades(Integer collegeId);

    /**
     * 是否可以导入学生信息信息
     * @param gradeId 班级id
     * @return
     */
    boolean isUserImportEnable(int gradeId) throws UserImportedException;

    /**
     * 是否可导入课程信息
     * @param gradeId 班级id
     * @return
     */
    boolean isLessonImportEnable(int gradeId);

    /**
     * 切换学院的学生信息导入状态
     * @param collegeId
     */
    void toggleUserImport(int collegeId);

    /**
     * 切换学院的课程信息导入状态
     * @param collegeId
     */
    void toggleLessonImport(int collegeId);

    /**
     * 学院是否开启用户导入
     * @param collegeId
     * @return
     */
    boolean isCollegeUserImportEnable(int collegeId);

    /**
     * 学院是否开启课程导入
     * @param collegeId
     * @return
     */
    boolean isCollegeLessonImportEnable(int collegeId);

    /**
     * 创建学校并指定名称
     * @param school 学校
     * 自动生成的id会装填到school的id中
     */
    void addSchool(School school) throws ParameterInvalidException;

    /**
     * 更新学校
     * @param school 学校对象(详细请看SchoolDao.updateSchool(School school)方法)
     */
    void updateSchool(School school) throws ParameterInvalidException;

    /**
     * 删除学校
     * @param id 学校id
     */
    void deleteSchool(int id) throws ParameterInvalidException;

    /**
     * 添加学院
     * @param college 学院
     * @param schoolId 所属学校
     * @throws ParameterInvalidException 当学院名称为空则抛出异常
     */
    void addCollege(College college,int schoolId) throws ParameterInvalidException;

    /**
     * 更新学院
     * @param college 学院对象(详细请看CollegeDao.updateCollege(College college)方法)
     */
    void updateCollege(College college) throws ParameterInvalidException,DataConflictException;

    /**
     * 删除学院
     * @param id 学院id
     * @throws ParameterInvalidException 当返回的影响记录条数为0则抛出
     */
    void deleteCollege(int id) throws ParameterInvalidException;

    /**
     * 添加班级
     * @param grade 班级信息
     */
    void addGrade(Grade grade) throws ParameterInvalidException, InternalServerException;

    /**
     * 更新班级信息
     * @param grade
     */
    void updateGrade(Grade grade) throws ParameterInvalidException,DataConflictException;

    /**
     * 删除班级
     * @param id 班级id
     */
    void deleteGrade(int id) throws ParameterInvalidException;

    /**
     * 获取全部学校信息
     * @param field 排序字段
     * @param isAsc 是否升序
     * @return
     */
    List<School> getSchools(String field,boolean isAsc);

    /**
     * 学校管理员登录（由于学校管理员的信息只有id和pwd，所以不需要返回额外信息）
     * @param id 管理员id
     * @param pwd 密码
     * @return 是否成功
     */
    boolean schoolManagerLogin(String id,String pwd) throws DataNotFoundException;
}
