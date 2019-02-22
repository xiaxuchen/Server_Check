package com.cxyz.check.service.impl;

import com.cxyz.check.dao.PushDao;
import com.cxyz.check.dto.PushDto;
import com.cxyz.check.entity.Push;
import com.cxyz.check.exception.push.NoPushException;
import com.cxyz.check.exception.push.PushAddException;
import com.cxyz.check.exception.push.PushException;
import com.cxyz.check.service.PushService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private PushDao dao;

    /**
     * 获取推送信息
     * @param id 用户id
     * @param type 用户类型
     * @return
     */
    public List<PushDto> getPushes(String id, Integer type)
    {
        List<PushDto> pushes = dao.getPushes(id, type);
        if(pushes.isEmpty())
            throw new NoPushException("没有推送信息");
        return pushes;
    }

    /**
     * 添加推送信息
     * @param pushes
     */
    public void addPushes(List<Push> pushes)
    {
        final int count = dao.addPushes(pushes);
        if (count == 0)
            throw new PushAddException("未知错误,推送失败");
    }


    /**
     * 推送成功
     * @param id 推送id
     */
    public void pushSuccess(Integer id)
    {
        final int count = dao.pushSuccess(id);
        if(count == 0)
            throw new PushException("更新失败");
    }
}
