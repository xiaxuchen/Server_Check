package com.cxyz.check.dto;

import com.cxyz.check.entity.Vacate;


public class VacateDto extends Vacate {

    private Integer state;//审核状态

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "VacateDto{" +
                "state=" + state+super.toString()+
                '}';
    }
}
