package com.cxyz.check.dao;

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
}
