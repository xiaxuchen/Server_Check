package com.cxyz.check.dao;

import com.cxyz.check.entity.Vacate;

import org.apache.ibatis.annotations.Param;

public interface VacateDao {

    /**
     * 添加请假信息
     * @param vacate
     * @return
     */
    int addVacate(@Param("vacate") Vacate vacate);
}
