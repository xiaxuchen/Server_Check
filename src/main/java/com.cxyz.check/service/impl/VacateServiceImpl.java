package com.cxyz.check.service.impl;

import com.cxyz.check.dao.VacateDao;
import com.cxyz.check.entity.Vacate;
import com.cxyz.check.exception.vacate.VacateException;
import com.cxyz.check.exception.vacate.VacateFailException;
import com.cxyz.check.service.VacateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacateServiceImpl implements VacateService {

    @Autowired
    private VacateDao vacateDao;

    @Override
    public void vacate(Vacate vacate) {
        //计算请假时长,推送给老师
        int count = vacateDao.addVacate(vacate);
        if(count == 0)
            throw new VacateFailException("插入失败");
    }
}
