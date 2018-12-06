package com.cxyz.check.service;

import com.cxyz.check.dto.CheckRecordDto;

public interface RecordService {

    /**
     * 获取最近10条考勤违规记录
     * @param id 用户id
     * @param type 用户类型
     * @param grade 用户班级id
     * @return
     */
    CheckRecordDto getCheckRecord(String id, int type, int grade);
}
