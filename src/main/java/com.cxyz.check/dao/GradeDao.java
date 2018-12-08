package com.cxyz.check.dao;

import org.apache.ibatis.annotations.Param;

public interface GradeDao {

    /**
     * 通过班级id获取所属学校id
     * @return 所属学校id
     */
    Integer getGradeSchoolId(@Param("gradeId")Integer gradeId);
}
