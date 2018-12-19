package com.cxyz.check.dao;

import com.cxyz.check.dto.AlterRecordDto;
import com.cxyz.check.entity.CheckRecord;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpdateDao {

    /**
     * 添加更新
     * @param records 历史记录
     * @param updaterId 更新人
     * @param updaterType 更新人类型
     * @return
     */
    int addUpdates(@Param("records") List<CheckRecord> records,@Param("updaterId") String updaterId,
                   @Param("updaterType") Integer updaterType);
}
