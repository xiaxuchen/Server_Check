package com.cxyz.check.handler;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.PushDto;
import com.cxyz.check.entity.Push;
import com.cxyz.check.exception.push.NoPushException;
import com.cxyz.check.exception.push.PushException;
import com.cxyz.check.service.PushService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/push")
public class PushHandler {

    @Autowired
    private PushService pushService;


    @RequestMapping(value = "/getPushes",
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    /**
     * 获取推送信息
     * @param id 用户id
     * @param type 用户类型
     * @return
     */
    public CheckResult<List<PushDto>> getPushes(@RequestParam String id,@RequestParam Integer  type)
    {

        CheckResult<List<PushDto>> cr = new CheckResult<>();
        try {
            cr.setData(pushService.getPushes(id,type));
        }catch (NoPushException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("未知错误");
        }

        return cr;
    }

    @RequestMapping(value = "/pushSuccess",
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    /**
     * 推送成功
     * @param id 推送id
     * @return
     */
    public CheckResult pushSuccess(@RequestParam Integer id)
    {
        CheckResult cr = new CheckResult();
        try {
            pushService.pushSuccess(id);
            cr.setSuccess(true);
        }catch (PushException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("未知错误");
        }
        return cr;
    }
}
