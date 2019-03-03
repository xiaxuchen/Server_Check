package com.cxyz.check.dao;

import com.cxyz.check.dto.VacateDto;
import com.cxyz.check.entity.Photo;
import com.cxyz.check.entity.Vacate;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface VacateDao {

    /**
     * 添加请假信息
     * @param vacate
     * @return
     */
    int addVacate(@Param("vacate") Vacate vacate);

    /**
     * 获取个人的请假信息
     * @param sponsorId 请假人id
     * @param sponsorType 请假人类型
     * @param state 查看的状态
     * @return
     */
    List<VacateDto> getVacates(@Param("sponsorId")String sponsorId, @Param("sponsorType")Integer sponsorType,@Param("state")Integer state);

    /**
     * 获取请假信息去审核
     * @param checkerId
     * @param state
     * @return
     */
    List<VacateDto> getVacatesToAudit(@Param("checkerId")String checkerId,@Param("state")Integer state);

    /**
     * 获取请假人id
     * @param vacId 请假id
     * @return
     */
    String getVacateSponsorId(@Param("vacId")Integer vacId);

    /**
     * 上传图片路径信息到数据库
     *
     * @param photos 图片路径
     * @param vacId 请假id
     * @return
     */
    int uploadPhotos(@Param("photos") List<Photo> photos,@Param("vacId") Integer vacId);

    /**
     * 添加日期信息
     * @param dates 日期信息
     * @param vacId 请假id
     * @return
     */
    int addDates(@Param("dates")List<Date> dates,@Param("vacId")Integer vacId);

    /**
     * 获取请假的照片
     * @param id 请假id
     * @return
     */
    List<String> getPhotos(@Param("id")Integer id);

    /**
     *  假删除一张请假的图片(将相应id的active设为1)
     * @param id 图片id
     * @return 更新数量
     */
    int deletePhoto(@Param("id") Integer id);

    /**
     * 获取请假的日期
     * @param id 请假id
     * @return
     */
    List<Date> getDates(@Param("id")Integer id);

    /**
     * 上传图片路径信息到数据库
     * @param photo 图片信息
     * @return
     */
    int uploadPhoto(@Param("photo") Photo photo,@Param("vacId")Integer vacId);

    /**
     * 获取班级的请假条，如果开始时间等于结束时间，则查询的是当天的
     * @param gradeId 班级id
     * @param from 开始日期
     * @param to 结束日期
     * @param withTime 是否附带时间
     * @return
     */
    List<Vacate> getGradeVacateInDates(@Param("gradeId")Integer gradeId,@Param("from")String from,@Param("to")String to,@Param("withTime") boolean withTime);

}
