package com.cxyz.check.service;

import com.cxyz.check.entity.Vacate;
import com.cxyz.check.exception.vacate.VacateFailException;

public interface VacateService {

    /**
     * 请假，将请假信息插入tb_vac
     * @param vacate
     * @throws VacateFailException 添加到tb_vac失败时抛出
     */

    void vacate(Vacate vacate) throws VacateFailException;
}
