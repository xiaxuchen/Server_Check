package com.cxyz.check.dto;

import com.cxyz.check.custom.ResultCustom;

import java.util.Date;
import java.util.List;

public class StatisticDto {

    //考勤记录
    private List<ResultCustom> results;

    private int personCount;

    public List<ResultCustom> getResults() {
        return results;
    }

    public void setResults(List<ResultCustom> results) {
        this.results = results;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    @Override
    public String toString() {
        return "StatisticDto{" +
                "results=" + results +
                ", personCount=" + personCount +
                '}';
    }
}
