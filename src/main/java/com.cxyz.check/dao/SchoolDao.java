package com.cxyz.check.dao;


import com.cxyz.check.entity.School;

import org.apache.ibatis.annotations.Param;

public interface SchoolDao {

    /**
     * 获取当前学期id
     * @param schoolId 学校id
     * @return 当前学期
     */
    Integer getCurrentTermId(@Param("schoolId")int schoolId);

    /**
     * 创建学校
     * @param name 学校名称
     * @return
     */
    int addSchool(@Param("school") School school);

    /**
     * 更新学校信息
     * @param school
     * @return
     */
    int updateSchool(@Param("school") School school);

    /**
     * 删除学校
     * @param id 学校id
     * @return
     */
    int deleteSchool(@Param("id") int id);
}
