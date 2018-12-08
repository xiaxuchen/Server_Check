package com.cxyz.check.dao;

import com.cxyz.check.util.date.Date;

import org.apache.ibatis.annotations.Param;

public interface SchoolDao {

    /**
     * 获取当前学期id
     * @param schoolId 学校id
     * @return 当前学期
     */
    Integer getCurrentTermId(@Param("schoolId")int schoolId);
}
