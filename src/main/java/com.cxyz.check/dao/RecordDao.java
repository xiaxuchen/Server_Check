package com.cxyz.check.dao;

import com.cxyz.check.custom.ResultCustom;
import com.cxyz.check.dto.AlterRecordDto;
import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.MyHistoryDto;
import com.cxyz.check.dto.StatisticRecordDto;
import com.cxyz.check.entity.CheckRecord;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RecordDao {

    /**
     * 获取个人历史考勤记录
     * @param id 学生id
     * @param result 考勤结果类型
     * @param start 起始
     * @param len 长度
     * @return
     */
    List<MyHistoryDto> getMyHistory(@Param("id")String id,@Param("result")Integer result,@Param("start")int start,@Param("len")int len);

    /**
     * 通过学生的id查询他的记录
     * @param id 学生id
     * @return
     */
    List<ResultCustom> getRecordStatistic(@Param("id") String id);

    /**
     * 获取一个课次的考勤记录信息
     * @param compId
     * @return
     */
    List<CheckRecord> getRecordsByCompId(@Param("compId")Integer compId);
    /**
     * 添加违规学生记录
     * @param stuInfos 学生信息
     * @return 影响记录数
     */
    int addRecords(@Param("id") int id,
                   @Param("stuInfo") List<CommitCheckDto.StuInfo>
                           stuInfos);

    /**
     * 删除违规学生记录
     * @param stuInfos 学生信息
     * @return 影响记录数
     */
    int removeRecords(@Param("id") int id,
                      @Param("stuInfo") List<CommitCheckDto.StuInfo>
                              stuInfos);

    /**
     * 添加特殊情况
     * @param id 完成情况id
     * @param des 描述信息
     * @return
     */
    int addOtherState(@Param("id")int id,@Param("des")String des);

    /**
     * 通过考勤人id和类型获取考勤历史纪录,获取10条
     * @param id 考勤人id
     * @param type 考勤人类型
     * @param start 开始条目
     * @param len 条目数
     * @return
     */
    List<CheckHistoryDto> getHistory(@Param("id")String id ,@Param("type") int type,
                                     @Param("start")int start,@Param("len")int len);

    /**
     * 获取考勤结果记录
     * @param gradeId 班级id
     * @param compId 考勤id
     * @return 结果
     */
    List<AlterRecordDto> getAlterRecords(@Param("gradeId") Integer gradeId,@Param("compId") Integer compId);

    /**
     * 获取将要被修改的记录
     * @param stuId 学生id
     * @param compId 完成情况id
     * @return
     */
    CheckRecord getAlterRecord(@Param("stuId") String stuId,@Param("compId")Integer compId);
    /**
     * 更改考勤结果
     * @param alters
     * @return
     */
    int updateRecord(@Param("compId") Integer compId,@Param("alter")AlterRecordDto alters);

    /**
     * 获取班级时间段的考勤统计
     * @param start 开始时间
     * @param end 结束时间
     * @param gradeId 班级id
     * @return
     */
    List<ResultCustom> getResults(@Param("start") String start, @Param("end")String end,@Param("gradeId")Integer gradeId);

    /**
     * 统计页面获取记录详情
     * @param start 开始时间
     * @param end 结束时间
     * @param gradeId 班级id
     * @param resultType 结果
     * @return
     */
    List<StatisticRecordDto> getStatisticRecords(@Param("start") String start, @Param("end")String end,@Param("gradeId")Integer gradeId,@Param("resultType")Integer resultType);

    /**
     * 获取课程的考勤信息
     * @param id 课程id
     * @return
     */
    List<CheckHistoryDto> getLessonHistories(@Param("id")Integer id,@Param("start")int start,@Param("len")int len);

}
