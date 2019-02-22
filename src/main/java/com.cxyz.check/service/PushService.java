package com.cxyz.check.service;

import com.cxyz.check.dao.PushDao;
import com.cxyz.check.dto.PushDto;
import com.cxyz.check.entity.Push;
import com.cxyz.check.exception.push.NoPushException;
import com.cxyz.check.exception.push.PushAddException;
import com.cxyz.check.exception.push.PushException;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PushService {

    /**
     * 获取推送信息
     * @param id 用户id
     * @param type 用户类型
     * @return
     */
    List<PushDto> getPushes(String id, Integer type) throws NoPushException;

    /**
     * 添加推送信息
     * @param pushes
     */
    void addPushes(List<Push> pushes) throws PushAddException;


    /**
     * 推送成功
     * @param id 推送id
     */
    void pushSuccess(Integer id) throws PushException;
}
