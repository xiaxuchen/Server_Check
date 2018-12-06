package com.cxyz.check.dao;

import com.cxyz.check.entity.College;
import com.cxyz.check.entity.Grade;

import org.apache.ibatis.annotations.Param;


public interface EnvironmentDao {

    /**
     * 通过班级id获取添加user时需要的信息
     * @param id 班级id
     * @return 包含学院名称，学院id和班级名称的grade对象
     */
    College getUserNeedById(@Param("id") int id);
}
