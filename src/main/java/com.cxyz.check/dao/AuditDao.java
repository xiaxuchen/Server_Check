package com.cxyz.check.dao;

import com.cxyz.check.entity.Audit;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface AuditDao {

    /**
     * 添加审核信息
     * @param audits 审核信息
     * @return 更新的记录数
     */
    int addAudits(@Param("audits") Set<Audit> audits);

    /**
     * 审核请假
     * @param audit
     * @return
     */
    int auditVac(@Param("audit")Audit audit);

    /**
     * 清除其他的审核
     * @param auditedId 审核完成id
     * @param power 权限
     * @return
     */
    int clearOther(@Param("auditedId")Integer auditedId,@Param("power")Integer power);


    /**
     * 获取请假的审核信息
     * @param vacId
     * @return
     */
    List<Audit> getAudits(@Param("vacId")Integer vacId,@Param(value = "checkerId")String checkerId);

    /**
     * 获取请假id
     * @param id 审核id
     * @return
     */
    Integer getVacId(@Param("id")Integer id);
}
