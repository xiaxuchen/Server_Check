package com.cxyz.check.dao;

import com.cxyz.check.entity.CheckRecord;

import java.util.List;

public interface CheckRecordDao {

    /**
     * 通过学生的id查询他的记录
     * @param id 学生id
     * @param start 开始查询的位置
     * @param len 查询条目数
     * @return
     */
    List<CheckRecord> getRecordByStuId(String id, int start, int len);

    /**
     * 加载详细考勤信息
     * @param id 考勤记录id
     * @return
     */
    CheckRecord getSingleRecordById(int id);

    /**
     * 提交考勤记录
     * @param list 考勤记录
     */
    void addRecords(List<CheckRecord> list);
}
