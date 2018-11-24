package com.cxyz.check.dao;

import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.entity.CheckRecord;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordDao {

    /**
     * 通过学生的id查询他的记录
     * @param id 学生id
     * @param start 开始查询的位置
     * @param len 查询条目数
     * @return
     */
    List<CheckRecord> getCheckRecords(@Param("id") String id,
                                    @Param("start") int start,
                                    @Param("len") int len);

    /**
     * 加载详细考勤信息
     * @param id 考勤记录id
     * @return
     */
    CheckRecord getSingleRecordById(int id);

    /**
     * 添加违规学生记录
     * @param stuInfos 学生信息
     * @return 影响记录数
     */
    int addRecords(@Param("id") int id,
                   @Param("stuInfo") List<CommitCheckDto.StuInfo>
                           stuInfos);

    /**
     * 添加特殊情况
     * @param id 完成情况id
     * @param des 描述信息
     * @return
     */
    int addOtherState(@Param("id")int id,@Param("des")String des);
}
