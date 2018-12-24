package com.cxyz.check.handler;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.entity.User;
import com.cxyz.check.entity.Vacate;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.service.VacateService;
import com.cxyz.check.util.parse.GsonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

@Controller
@RequestMapping("/vacate")
public class VacateHandler {

    @Autowired
    private VacateService vacateService;

    @RequestMapping(value = "/vacate",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8;"

    )
    @ResponseBody
    public CheckResult<String> vacate(@RequestParam String start,@RequestParam String end,
                                      @RequestParam String des,@RequestParam String sponsorId)
    {
        CheckResult<String> cr = new CheckResult<>();
        try {
            Vacate vacate  = new Vacate();
            vacate.setSponsor(new User(sponsorId));
            vacate.setDes(des);
            vacate.setStart((Timestamp) GsonUtil.fromJson(start, Timestamp.class));
            vacate.setEnd((Timestamp) GsonUtil.fromJson(end, Timestamp.class));
            vacateService.vacate(vacate);
            cr.setData("提交成功");
        }catch (GsonException e)
        {
            cr.setError("请假数据异常");
        }catch (Exception e)
        {
            cr.setError("提交失败");
        }
        return cr;
    }
}
