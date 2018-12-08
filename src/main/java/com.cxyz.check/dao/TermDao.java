package com.cxyz.check.dao;


import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface TermDao {

    /**
     * 获取开学日期
     * @param schoolId 学校id
     * @return 开学日期
     */
    Date getTermStart(@Param("schoolId")Integer schoolId);
}
