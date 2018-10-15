package com.cxyz.check.dao;

import com.cxyz.check.entity.Teacher;

import org.apache.ibatis.annotations.Param;

//测试成功
public interface TeacherDao {

    /**
     * 通过工号获取老师信息
     * @param id 老师工号
     * @return 单个老师的所有信息
     */
    public Teacher getTeaById(@Param("id") String id);
}
