package com.cxyz.check.dao;

import com.cxyz.check.entity.College;
import com.cxyz.check.entity.School;

import org.apache.ibatis.annotations.Param;

public interface CollegeDao {

    /**
     * 用户导入是否开启
     * @param collegeId 学院id
     * @return
     */
    boolean isUserImportEnable(@Param("collegeId") int collegeId);

    /**
     * 课程导入是否开启
     * @param collegeId 学院id
     * @return
     */
    boolean isLessonImportEnable(@Param("collegeId") int collegeId);

    /**
     * 切换用户信息导入
     * @param collegeId
     * @return
     */
    int toggleUserImport(@Param("collegeId") int collegeId);

    /**
     * 切换课程信息导入
     * @param collegeId
     * @return
     */
    int toggleLessonImport(@Param("collegeId") int collegeId);

    /**
     * 创建学院
     * @param college 需要有name属性，将生成自增id
     * @param schoolId 所属学校id
     * @return
     */
    int addCollege(@Param("college") College college,@Param("schoolId") int schoolId);

    /**
     * 更新学院信息
     * @param college
     * @return
     */
    int updateCollege(@Param("college") College college);

    /**
     * 删除学院
     * @param id 学院id
     * @return
     */
    int deleteCollege(@Param("id") int id);
}
