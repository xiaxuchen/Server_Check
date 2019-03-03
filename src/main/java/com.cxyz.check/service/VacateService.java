package com.cxyz.check.service;

import com.cxyz.check.dto.VacateDto;
import com.cxyz.check.entity.Audit;
import com.cxyz.check.entity.Photo;
import com.cxyz.check.entity.Vacate;
import com.cxyz.check.exception.vacate.PhotoDeleteException;
import com.cxyz.check.exception.vacate.VacateFailException;

import java.util.List;

public interface VacateService {

    /**
     * 请假，将请假信息插入tb_vac
     * @param vacate
     * @throws VacateFailException 添加到tb_vac失败时抛出
     */

    void vacate(Vacate vacate) throws VacateFailException;

    /**
     * 审核请假
     * @param audit
     */
    void auditVac(Audit audit);

    /**
     * 获取请假信息
     * @param sponsorId 发起人id
     * @param sponsorType 发起人类型
     * @param state 状态
     * @return
     */
    List<VacateDto> getVacates(String sponsorId,Integer sponsorType,Integer state);

    /**
     * 获取请假信息
     * @param checkerId 审核人id
     * @param state 状态
     * @return
     */
    List<VacateDto> getVacatesToAudit(String checkerId,Integer state);

    /**
     * 上传请假条
     * @param dto 请假条信息
     */
    void uploadVacate(VacateDto dto);

    /**
     * 根据id删除图片
     * @param id 图片id
     */
    void deletePhoto(Integer id) throws PhotoDeleteException;

    /**
     * 上传图片
     * @param paths 图片路径
     * @param vacId 请假id
     * @return
     */
    List<Photo> uploadPhotos(List<String> paths, Integer vacId);

    List<Vacate> getVacatesInDates(Integer gradeId,String from,String to);
}
