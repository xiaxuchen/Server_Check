package com.cxyz.check.dao;

import com.cxyz.check.dto.PushDto;
import com.cxyz.check.entity.Push;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PushDao {

    /**
     * 获取推送信息
     * @param id 用户id
     * @param type 用户类型
     */
    List<PushDto> getPushes(@Param("id") String id, @Param("type") Integer type);

    /**
     * 推送成功
     * @param id 推送信息id
     * @return
     */
    int pushSuccess(@Param("id") Integer id);

    /**
     * 添加推送信息
     * @param pushes 推送信息
     * @return
     */
    int addPushes(@Param("pushes") List<Push> pushes);
}
