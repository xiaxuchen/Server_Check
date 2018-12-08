package com.cxyz.check.dao;

import com.cxyz.check.entity.Times;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimesDao {

    /**
     * 获取学期时间表
     * @param termId 学期id
     * @return
     */
    List<Times> getTermTimes(@Param("termId") int termId);
}
