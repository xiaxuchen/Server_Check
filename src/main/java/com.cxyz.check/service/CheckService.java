package com.cxyz.check.service;

import com.cxyz.check.dto.CheckCommitDto;
import com.cxyz.check.entity.CheckRecord;

import java.util.List;

public interface CheckService {

    /**
     * 查询自己的违规记录
     * @param id 用户id
     * @param start 开始位置
     * @return 用户的违规记录
     */
    List<CheckRecord> getMineRecords(String id, int start);

    /**
     * 提交考勤记录
     * @param commitDto 考勤记录
     * @return
     */
    boolean CommitRecord(CheckCommitDto commitDto);

    /**
     * 检查是否有考勤任务
     */

}
